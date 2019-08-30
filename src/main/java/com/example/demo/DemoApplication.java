package com.example.demo;

import com.example.demo.job.ElasticJobService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Resource
    private ElasticJobService elasticJobService;


    @Override
    public void run(String... strings) throws Exception {
        elasticJobService.scanAddJob();
    }

}
