package com.demo.task.tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.task.tracker.models.Task;

public interface TaskRepository  extends JpaRepository<Task,Long> {
     
}
