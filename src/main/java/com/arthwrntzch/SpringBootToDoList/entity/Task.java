package com.arthwrntzch.SpringBootToDoList.entity;

import com.arthwrntzch.SpringBootToDoList.enums.TaskStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

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
  private int userId;

  private String name;

  private String description;

  @Enumerated(EnumType.STRING)
  private TaskStatus status;

  private LocalDate dueDate;
}
