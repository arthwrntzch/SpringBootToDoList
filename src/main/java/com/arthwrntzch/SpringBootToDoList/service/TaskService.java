package com.arthwrntzch.SpringBootToDoList.service;

import com.arthwrntzch.SpringBootToDoList.dto.TaskDto;
import com.arthwrntzch.SpringBootToDoList.entity.Task;
import com.arthwrntzch.SpringBootToDoList.enums.TaskStatus;
import com.arthwrntzch.SpringBootToDoList.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public Task createTask(Task task) {
        if (task.getName().isBlank()){throw new EmptyResultDataAccessException(1);}
        if (task.getDescription().isBlank()){throw new EmptyResultDataAccessException(2);}
        if (task.getStatus() == null){throw new EmptyResultDataAccessException(3);}
        if (task.getDueDate() == null){throw new EmptyResultDataAccessException(4);}
        if (task.getUserId()){throw new EmptyResultDataAccessException(5);}
        return taskRepository.save(task);
    }

    public Task updateTask(int id, Task patch) {
        Task taskToUpdate = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: id=" + id));

        if (patch.getName() != null && !patch.getName().isBlank()) {
            taskToUpdate.setName(patch.getName());
        }
        if (patch.getDescription() != null && !patch.getDescription().isBlank()) {
            taskToUpdate.setDescription(patch.getDescription());
        }
        if (patch.getStatus() != null) {
            taskToUpdate.setStatus(patch.getStatus());
        }
        return taskRepository.save(taskToUpdate);
    }

    public void deleteTask(int id) {
        try {
            taskRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new IllegalArgumentException("Task not found: id=" + id);
        }
    }

    @Transactional(readOnly = true)
    public Task getById(int id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: id=" + id));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }


    private static void validateTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task must not be null");
        }
        if (task.getName() == null || task.getName().isBlank()) {
            throw new IllegalArgumentException("Task name must not be blank");
        }
        if (task.getDescription() == null || task.getDescription().isBlank()) {
            throw new IllegalArgumentException("Task description must not be blank");
        }
        if (task.getStatus() == null) {
            throw new IllegalArgumentException("Task status must not be null");
        }
    }

    private static void validateTaskDto(TaskDto taskDto) {
        if (taskDto == null) {
            throw new IllegalArgumentException("TaskDto must not be null");
        }
        if (taskDto.getName() == null || taskDto.getName().isBlank()) {
            throw new IllegalArgumentException("Task name must not be blank");
        }
        if (taskDto.getDescription() == null || taskDto.getDescription().isBlank()) {
            throw new IllegalArgumentException("Task description must not be blank");
        }
        if (taskDto.getStatus() == null || taskDto.getStatus().isBlank()) {
            throw new IllegalArgumentException("Task status must not be blank");
        }
    }


    private static TaskDto toDto(Task task) {
        validateTask(task);

        return TaskDto.builder()
                .name(task.getName())
                .description(task.getDescription())
                .status(task.getStatus().toString())
                .build();
    }

    private static Task toTask(TaskDto taskDto) {
        validateTaskDto(taskDto);

        Task task = new Task();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setStatus(TaskStatus.valueOf(taskDto.getStatus()));
        return task;
    }

}
