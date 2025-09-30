package com.arthwrntzch.SpringBootToDoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.arthwrntzch.SpringBootToDoList.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    
}
