package com.arthwrntzch.SpringBootToDoList.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "userId")
    private Set<Task> tasks = new HashSet<>();

    public User(int id) {
        this.id = id;
    }

    public User() {
    }
}

