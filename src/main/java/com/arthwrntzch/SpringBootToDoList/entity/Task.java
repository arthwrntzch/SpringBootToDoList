package com.arthwrntzch.SpringBootToDoList.entity;

import com.arthwrntzch.SpringBootToDoList.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "tasks")
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false, length = 64, name = "name")
  private String name;

  @Column(columnDefinition = "TEXT", name = "description")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, name = "status")
  private TaskStatus status;

  @Column(name = "due_date")
  private LocalDate dueDate;
}
