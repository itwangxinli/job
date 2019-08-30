package com.example.demo.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<JobTask, Long> {
    List<JobTask> findByStatus(int status);
}
