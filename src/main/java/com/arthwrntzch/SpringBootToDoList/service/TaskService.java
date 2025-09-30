package com.arthwrntzch.SpringBootToDoList.service;

import com.arthwrntzch.SpringBootToDoList.dto.TaskDto;
import com.arthwrntzch.SpringBootToDoList.entity.Task;
import com.arthwrntzch.SpringBootToDoList.entity.User;
import com.arthwrntzch.SpringBootToDoList.enums.TaskStatus;
import com.arthwrntzch.SpringBootToDoList.repository.TaskRepository;
import com.arthwrntzch.SpringBootToDoList.repository.UserRepository;





import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
            UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Task createTask(int userId, TaskDto taskDto) {
        Task task = new Task();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setStatus(parseStatus(taskDto.getStatus()));
        task.setDueDate(taskDto.getDueDate());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "User not found: " + userId));
        task.setUserId(user);

        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(int taskId, TaskDto taskDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Task not found: " + taskId));

        if (taskDto.getName() != null)
            task.setName(taskDto.getName());
        if (taskDto.getDescription() != null)
            task.setDescription(taskDto.getDescription());
        if (taskDto.getStatus() != null) {
            TaskStatus status = parseStatus(taskDto.getStatus());
            if (status != null)
                task.setStatus(status);
        }
        if (taskDto.getDueDate() != null)
            task.setDueDate(taskDto.getDueDate());

        if (taskDto.getUserId() > 0) {
            User user = userRepository.findById(taskDto.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "User not found: " + taskDto.getUserId()));
            task.setUserId(user); 
        }

        return taskRepository.save(task);
    }

    @Transactional
    public boolean deleteTask(int id) {
        if (!taskRepository.existsById(id))
            return false;
        taskRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public TaskDto getById(int id) {
        return taskRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getAllTasks() {
        List<TaskDto> result = new ArrayList<>();
        for (Task t : taskRepository.findAll())
            result.add(toDto(t));
        return result;
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getAllByUserId(int userId) {
        List<TaskDto> result = new ArrayList<>();
        for (Task t : taskRepository.findByUserId_Id(userId))
            result.add(toDto(t)); // или findByUser_Id
        return result;
    }

    private TaskStatus parseStatus(String raw) {
        if (raw == null)
            return null;
        try {
            return TaskStatus.valueOf(raw.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null; // или кинуть 400
        }
    }

    public TaskDto toDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setStatus(
                task.getStatus() != null ? task.getStatus().toString() : null);
        dto.setDueDate(task.getDueDate());
        dto.setUserId(task.getUserId() != null ? task.getUserId().getId() : 0); // или
                                                                                // getUser()
        return dto;
    }
}
