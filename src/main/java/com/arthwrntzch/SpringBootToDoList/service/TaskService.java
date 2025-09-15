package com.arthwrntzch.SpringBootToDoList.service;

import com.arthwrntzch.SpringBootToDoList.dto.TaskDto;
import com.arthwrntzch.SpringBootToDoList.entity.Task;
import com.arthwrntzch.SpringBootToDoList.entity.User;
import com.arthwrntzch.SpringBootToDoList.enums.TaskStatus;
import com.arthwrntzch.SpringBootToDoList.repository.TaskRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    @PersistenceContext
    private EntityManager em;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(int userId, TaskDto task) {
        Task taskEntity = new Task();
        taskEntity.setName(task.getName());
        taskEntity.setDescription(task.getDescription());
        taskEntity.setStatus(TaskStatus.valueOf(task.getStatus()));
        taskEntity.setDueDate(task.getDueDate());
        User userRef = em.getReference(User.class, userId);
        taskEntity.setUserId(userRef);
        return taskRepository.save(taskEntity);
    }

    public Task updateTask(int taskId, TaskDto taskdto) {
        Task taskEntity = taskRepository.findById(taskId).orElse(null);
        if (taskEntity == null) return null;
        taskEntity.setName(taskdto.getName());
        taskEntity.setDescription(taskdto.getDescription());
        taskEntity.setStatus(TaskStatus.valueOf(taskdto.getStatus()));
        taskEntity.setDueDate(taskdto.getDueDate());
        return taskRepository.save(taskEntity);
    }

    public boolean deleteTask(int id) {
        if (!taskRepository.existsById(id)) return false;
        taskRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public TaskDto getById(int id) {
        return taskRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getAllByUserId(int userId) {
        return taskRepository.findByUserId_Id(userId).stream().map(this::toDto).toList();
    }

    public TaskDto toDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .status(task.getStatus() != null ? task.getStatus().toString() : null)
                .dueDate(task.getDueDate())
                .userId(task.getUserId() != null ? task.getUserId().getId() : 0)
                .build();
    }

    @SuppressWarnings("unused")
    private Task toTask(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setStatus(TaskStatus.valueOf(taskDto.getStatus()));
        task.setDueDate(taskDto.getDueDate());
        if (taskDto.getUserId() > 0) {
            task.setUserId(em.getReference(User.class, taskDto.getUserId()));
        }
        return task;
    }
}
