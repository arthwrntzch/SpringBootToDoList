package com.arthwrntzch.SpringBootToDoList.entity;

import com.arthwrntzch.SpringBootToDoList.enums.TaskStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Tasks")
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  private String name;
  private String description;
  private TaskStatus status;
  private LocalDateTime dueDate;
}
