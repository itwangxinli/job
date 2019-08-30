package com.example.demo.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaSimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("任务名：{}, 片数：{}, id={}", shardingContext.getJobName(), shardingContext.getShardingTotalCount(),
                shardingContext.getJobParameter());

    }
}
