package com.demo.task.tracker.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.demo.task.tracker.models.Task;
import com.demo.task.tracker.repositories.TaskRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskService {
    
    private final TaskRepository taskRepository;

    public List<Task> getAllTask(){
        return taskRepository.findAll();
    }

    public Task saveTask(Task newTask){
        return taskRepository.save(newTask);
    }

    public Optional<Task> findTaskById( Long id ){
        return taskRepository.findById(id);
    }

    public boolean deleteTask( Long id ){
        return findTaskById(id)
                .map(  task -> { 
                        taskRepository.deleteById(id);
                        return true;
                })
                .orElse(false);
    }

    public Optional<Task> updateTask( Long id, Task updatedTask ){
        return findTaskById(id)
            .map(  task -> taskRepository.save( updatedTask ) );
    }

    
    
}
