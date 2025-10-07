package com.arthwrntzch.SpringBootToDoList.repository;

import com.arthwrntzch.SpringBootToDoList.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
