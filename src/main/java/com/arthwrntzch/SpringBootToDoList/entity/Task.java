package com.arthwrntzch.SpringBootToDoList.entity;

import java.util.Date;


import com.arthwrntzch.SpringBootToDoList.enums.TaskStatus;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public int name;
    public String description;
    public TaskStatus status;
    public Date dueDate;
}
