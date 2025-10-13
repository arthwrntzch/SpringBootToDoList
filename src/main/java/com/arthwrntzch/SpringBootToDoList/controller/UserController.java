package com.arthwrntzch.SpringBootToDoList.controller;

import com.arthwrntzch.SpringBootToDoList.dto.UserDto;
import com.arthwrntzch.SpringBootToDoList.dto.mapping.UserMapping;
import com.arthwrntzch.SpringBootToDoList.entity.User;
import com.arthwrntzch.SpringBootToDoList.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapping userMapping;

    public UserController(UserService userService, UserMapping userMapping) {
        this.userService = userService;
        this.userMapping = userMapping;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User user = userMapping.toEntity(userDto);
        User created = userService.createUser(user);
        return ResponseEntity
                .created(URI.create("/api/users/" + created.getId()))
                .body(userMapping.toDto(created));
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream().map(userMapping::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(userMapping.toDto(user))
                : ResponseEntity.notFound().build();
    }
}
