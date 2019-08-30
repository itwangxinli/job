package com.example.demo.job;

import com.example.demo.data.JobTask;
import com.example.demo.data.TaskRepository;
import com.example.demo.util.CronUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ElasticJobService {
    @Resource
    private ElasticJobHandler jobHandler;
    @Resource
    private TaskRepository taskRepository;

    /**
     * 扫描db，并添加任务
     */
    public void scanAddJob() {

        List<JobTask> jobTasks = taskRepository.findByStatus(0);
        jobTasks.forEach(jobTask -> {
            Long current = System.currentTimeMillis();
            String jobName = "job" + jobTask.getSendTime();
            String cron;
            //说明消费未发送，但是已经过了消息的发送时间，调整时间继续执行任务
            if (jobTask.getSendTime() < current) {
                //设置为一分钟之后执行，把Date转换为cron表达式
                cron = CronUtils.getCron(new Date(current + 60000));
            } else {
                cron = CronUtils.getCron(new Date(jobTask.getSendTime()));
            }
            jobHandler.addJob(jobName, "0/5 * * * * ? ", 1, String.valueOf(jobTask.getId()));
        });
    }
}
