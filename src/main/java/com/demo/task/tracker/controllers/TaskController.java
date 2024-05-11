package com.demo.task.tracker.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.task.tracker.dto.TaskDto;
import com.demo.task.tracker.models.Task;
import com.demo.task.tracker.services.TaskService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping()
    public ResponseEntity<List<TaskDto>> getAllTask(){
        return  ResponseEntity.ok( taskService.getAllTask() );
    }

    @PostMapping()
    public ResponseEntity<TaskDto> postTask(  @RequestBody TaskDto newTask ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body( taskService.saveTask(newTask)  );
    }


    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> findTaskById( @PathVariable Long id ){
        return taskService.findTaskDtoById(id)
            .map( task -> ResponseEntity.status(HttpStatus.OK).body(task)  )
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build() );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask( @PathVariable Long id ){
        if(  taskService.deleteTask(id) ){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PutMapping( "/{id}" )
    public ResponseEntity<TaskDto> putTask( 
        @PathVariable Long id, 
        @RequestBody TaskDto updatedTask ){        
            return taskService.updateTask(id, updatedTask)
                        .map( ResponseEntity::ok )
                        .orElse( ResponseEntity.notFound().build() );
    }
    
}