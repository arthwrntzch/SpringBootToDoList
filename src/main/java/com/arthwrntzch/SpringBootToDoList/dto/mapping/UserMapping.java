package com.arthwrntzch.SpringBootToDoList.dto.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.arthwrntzch.SpringBootToDoList.dto.TaskDto;
import com.arthwrntzch.SpringBootToDoList.entity.Task;

@Mapper(componentModel = "spring")
public interface UserMapping {

    @Mapping(source = "user.id", target = "user")
    TaskDto toDto(Task task);

    @Mapping(source = "user", target = "user.id")
    Task toEntity(TaskDto taskDto);

}
