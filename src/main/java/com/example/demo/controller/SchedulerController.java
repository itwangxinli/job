package com.example.demo.controller;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.internal.schedule.JobRegistry;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.example.demo.job.JavaSimpleJob;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

public class SchedulerController {
    @Resource
    private ZookeeperRegistryCenter regCenter;

    @Resource
    private JobEventConfiguration jobEventConfiguration;


    private void add(final SimpleJob simpleJob, final String cron,  final int shardingTotalCount,
                     final String shardingItemParameters) {
        new SpringJobScheduler(simpleJob, regCenter,
                getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters),
                jobEventConfiguration).init();
    }

    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron, final int shardingTotalCount, final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName())).overwrite(true).build();
    }

    /**
     * 增加一个作业调度
     * @return 成功标识
     */
    @RequestMapping("/add")
    public boolean add(){
        add(new JavaSimpleJob(),"0/5 * * * * ?",3,"0=Beijing,1=Shanghai,2=Guangzhou");
        return true;
    }
    /**
     * 修改一个作业调度
     * @return 成功标识
     */
    @RequestMapping("/update")
    public boolean update(){
        JobRegistry.getInstance().getJobScheduleController(JavaSimpleJob.class.getName()).rescheduleJob("0/20 * * * * ?");
        return true;
    }
    /**
     * 删除一个作业调度
     * @return 成功标识
     */
    @RequestMapping("/delete")
    public boolean delete(){
        JobRegistry.getInstance().getJobScheduleController(JavaSimpleJob.class.getName()).shutdown();
        return true;
    }

}
