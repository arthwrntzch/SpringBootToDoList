package com.arthwrntzch.SpringBootToDoList.service;

import com.arthwrntzch.SpringBootToDoList.dto.TaskDto;
import com.arthwrntzch.SpringBootToDoList.entity.Task;
import com.arthwrntzch.SpringBootToDoList.enums.TaskStatus;
import com.arthwrntzch.SpringBootToDoList.repository.TaskRepository;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository, SessionFactory sessionFactory) {
        this.taskRepository = taskRepository;
    }

    public void create(TaskDto dto) {
        Task entity = new Task();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setStatus(TaskStatus.valueOf(dto.getStatus()));
        entity.setUser(dto.getUser());
        taskRepository.createTask(entity);
    }


    public TaskDto getById(int id) {
        TaskDto taskDto = toDto(taskRepository.getById(id));
        return taskDto;
    }

    public List<TaskDto> getAll() {
        return taskRepository.getAllTasks()
                .stream()
                .map(this::toDto)
                .toList();
    }


    public void update(int id, TaskDto dto) {
        Task existing = taskRepository.getById(id);
        if (existing == null) {
            throw new NoSuchElementException("Task not found: " + id);
        }

        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getStatus() != null) existing.setStatus(TaskStatus.valueOf(dto.getStatus()));
        if (dto.getUser() != null) existing.setUser(dto.getUser());
        if (dto.getDueDate() != null) existing.setDueDate(LocalDateTime.from(dto.getDueDate()));

        taskRepository.updateTask(existing);

    }


    public void delete(int id) {
        taskRepository.deleteTask(id);
    }

    private TaskDto toDto(Task t) {
        TaskDto dto = new TaskDto();
        dto.setId(t.getId());
        dto.setName(t.getName());
        dto.setDescription(t.getDescription());
        dto.setStatus(t.getStatus() != null ? t.getStatus().name() : null);
        dto.setUser(t.getUser());
        dto.setDueDate(LocalDate.from(t.getDueDate()));
        return dto;
    }
}
