package com.arthwrntzch.SpringBootToDoList.controller;

import com.arthwrntzch.SpringBootToDoList.dto.TaskDto;
import com.arthwrntzch.SpringBootToDoList.dto.mapping.TaskMapping;
import com.arthwrntzch.SpringBootToDoList.entity.Task;
import com.arthwrntzch.SpringBootToDoList.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapping taskMapping;

    public TaskController(TaskService taskService, TaskMapping taskMapping) {
        this.taskService = taskService;
        this.taskMapping = taskMapping;
    }

    @GetMapping("/list")
    public List<TaskDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getById(@PathVariable Long id) {
        TaskDto dto = taskService.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public List<TaskDto> getAllByUser(@PathVariable Long userId) {
        return taskService.getAllByUserId(userId);
    }

    @PostMapping("/create/task/{userId}")
    public ResponseEntity<TaskDto> createTask(
            @PathVariable("userId") Long userId,
            @RequestBody TaskDto taskDto) {
        Task created = taskService.createTask(userId, taskDto);
        TaskDto body = taskMapping.toDto(created);
        return ResponseEntity.created(URI.create("/api/tasks/" + created.getId())).body(body);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable Long taskId,
            @RequestBody TaskDto taskDto) {
        Task updated = taskService.updateTask(taskId, taskDto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taskMapping.toDto(updated));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        boolean deleted = taskService.deleteTask(taskId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
