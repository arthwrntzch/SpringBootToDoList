package com.arthwrntzch.SpringBootToDoList.controller;

import com.arthwrntzch.SpringBootToDoList.dto.TaskDto;
import com.arthwrntzch.SpringBootToDoList.entity.Task;
import com.arthwrntzch.SpringBootToDoList.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    public TaskController(TaskService taskService) { this.taskService = taskService; }

    @GetMapping("/list")
    public List<TaskDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getById(@PathVariable int id) {
        TaskDto dto = taskService.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public List<TaskDto> getAllByUser(@PathVariable int userId) {
        return taskService.getAllByUserId(userId);
    }

    @PostMapping("/create/task/{userId}")
    public ResponseEntity<TaskDto> createTask(
            @PathVariable("userId") int userId,
            @RequestBody TaskDto taskdto
    ) {
        Task created = taskService.createTask(userId, taskdto);
        TaskDto body = taskService.toDto(created);
        return ResponseEntity.created(URI.create("/api/tasks/" + created.getId())).body(body);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable int taskId,
            @RequestBody TaskDto taskdto
    ) {
        Task updated = taskService.updateTask(taskId, taskdto);
        return updated != null ? ResponseEntity.ok(taskService.toDto(updated)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable int taskId) {
        boolean deleted = taskService.deleteTask(taskId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
