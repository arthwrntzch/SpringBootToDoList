package com.arthwrntzch.SpringBootToDoList.dto;

import com.arthwrntzch.SpringBootToDoList.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private int id;
    private String name;
    private String description;
    private String status;
    private LocalDate dueDate;
    private User user;
}
