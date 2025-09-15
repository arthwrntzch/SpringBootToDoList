package com.arthwrntzch.SpringBootToDoList.repository;

import com.arthwrntzch.SpringBootToDoList.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByUserId_Id(int userId);
}
