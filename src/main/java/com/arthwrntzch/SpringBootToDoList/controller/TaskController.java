package com.arthwrntzch.SpringBootToDoList.controller;

import com.arthwrntzch.SpringBootToDoList.entity.Task;
import com.arthwrntzch.SpringBootToDoList.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/list")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/create")
    public void createTask(
            @RequestBody Task task
    ) {
        taskService.createTask(task);
    }


}
