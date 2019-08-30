package com.example.demo.job;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@Data
@ConfigurationProperties(prefix = "elaticjob.zookeeper")
public class ElasticJobConfig {
    private String serverLists;
    private String namespace;
    @Resource
    private HikariDataSource dataSource;

    @Bean
    public ZookeeperConfiguration zkConfig() {
        return new ZookeeperConfiguration(serverLists, namespace);
    }

    @Bean(initMethod = "init")
    @ConditionalOnBean(ZookeeperConfiguration.class)
    public ZookeeperRegistryCenter regCenter(ZookeeperConfiguration config) {
        return new ZookeeperRegistryCenter(config);
    }

    /**
     * 将作业运行的痕迹进行持久化到DB
     *
     * @return
     */
    @Bean
    public JobEventConfiguration jobEventConfiguration() {
        return new JobEventRdbConfiguration(dataSource);
    }

    @Bean
    public ElasticJobListener elasticJobListener() {
        return new com.example.demo.job.ElasticJobListener(100, 100);
    }
}

