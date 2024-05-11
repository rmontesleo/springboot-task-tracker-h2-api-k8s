package com.demo.task.tracker.services;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.demo.task.tracker.dto.TaskDto;
import com.demo.task.tracker.models.Task;
import com.demo.task.tracker.repositories.TaskRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskService {

        private final TaskRepository taskRepository;

        private static Function<Task, TaskDto> taskToDtoMapper = task -> new TaskDto(
                        task.getId(),
                        task.getText(),
                        task.getDay(),
                        task.getReminder());

        private static Function<TaskDto, Task> dtoToTaskMapper = dto -> Task
                        .builder()
                        .day(dto.day())
                        .text(dto.text())
                        .id(dto.id())
                        .reminder(dto.reminder())
                        .build();

        public List<TaskDto> getAllTask() {
                return taskRepository
                                .findAll()
                                .stream()
                                .map(taskToDtoMapper)
                                .collect(Collectors.toList());
        }

        public TaskDto saveTask(TaskDto newTask) {
                Task taskToPersist = dtoToTaskMapper.apply(newTask);
                taskToPersist.setId(null);
                taskRepository.save(taskToPersist);
                return taskToDtoMapper.apply(taskToPersist);
        }

        private Optional<Task> findTaskById(Long id) {
                return taskRepository.findById(id);
        }

        public Optional<TaskDto> findTaskDtoById(Long id) {
                return findTaskById(id)
                                .map(taskToDtoMapper);
        }

        public boolean deleteTask(Long id) {
                return findTaskById(id)
                                .map(task -> {
                                        taskRepository.deleteById(id);
                                        return true;
                                })
                                .orElse(false);
        }

        public Optional<TaskDto> updateTask(Long id, TaskDto updatedTaskDto) {
                return findTaskById(id)
                                .map( taskFound -> {
                                        Task taskToPersist = dtoToTaskMapper.apply(updatedTaskDto);
                                        taskToPersist.setId(id);
                                        return taskRepository.save(taskToPersist);
                                })
                                .map(taskToDtoMapper);

        }
}