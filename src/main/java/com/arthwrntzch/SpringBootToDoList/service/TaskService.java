package com.arthwrntzch.SpringBootToDoList.service;

import com.arthwrntzch.SpringBootToDoList.dto.TaskDto;
import com.arthwrntzch.SpringBootToDoList.dto.mapping.TaskMapping;
import com.arthwrntzch.SpringBootToDoList.entity.Task;
import com.arthwrntzch.SpringBootToDoList.entity.User;
import com.arthwrntzch.SpringBootToDoList.enums.TaskStatus;
import com.arthwrntzch.SpringBootToDoList.repository.TaskRepository;
import com.arthwrntzch.SpringBootToDoList.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapping taskMapping;

    public TaskService(TaskRepository taskRepository,
            UserRepository userRepository,
            TaskMapping taskMapping) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapping = taskMapping;
    }

    public Task createTask(Long userId, TaskDto taskDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        Task task = taskMapping.toEntity(taskDto);
        // подстрахуем: если статус не пришёл — зададим дефолт
        if (task.getStatus() == null)
            task.setStatus(TaskStatus.TODO);
        task.setUser(user);

        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, TaskDto taskDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + taskId));

        if (taskDto.getName() != null)
            task.setName(taskDto.getName());
        if (taskDto.getDescription() != null)
            task.setDescription(taskDto.getDescription());
        if (taskDto.getDueDate() != null)
            task.setDueDate(taskDto.getDueDate());
        if (taskDto.getStatus() != null) {
            try {
                task.setStatus(TaskStatus.valueOf(taskDto.getStatus().trim().toUpperCase()));
            } catch (IllegalArgumentException ignored) {
                /* можно кинуть 400 */ }
        }
        if (taskDto.getUserId() != null && taskDto.getUserId() > 0) {
            User newUser = userRepository.findById(taskDto.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + taskDto.getUserId()));
            task.setUser(newUser);
        }

        return taskRepository.save(task);
    }

    public boolean deleteTask(Long id) {
        if (!taskRepository.existsById(id))
            return false;
        taskRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public TaskDto getById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapping::toDto)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getAllTasks() {
        List<TaskDto> result = new ArrayList<>();
        for (Task t : taskRepository.findAll())
            result.add(taskMapping.toDto(t));
        return result;
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getAllByUserId(Long userId) {
        List<TaskDto> result = new ArrayList<>();
        for (Task t : taskRepository.findByUser_Id(userId))
            result.add(taskMapping.toDto(t));
        return result;
    }
}
