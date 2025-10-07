package com.arthwrntzch.SpringBootToDoList.controller;

import com.arthwrntzch.SpringBootToDoList.entity.User;
import com.arthwrntzch.SpringBootToDoList.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User created = userService.createUser(user);
        return ResponseEntity
                .created(URI.create("/api/users/" + created.getId()))
                .body(created);
    }

    @GetMapping
    public List<User> getAllUsers() { return userService.getAllUsers(); }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user)
                : ResponseEntity.notFound().build();
    }
}
