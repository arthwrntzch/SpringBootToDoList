package com.arthwrntzch.SpringBootToDoList.dto.mapping;

import com.arthwrntzch.SpringBootToDoList.dto.UserDto;
import com.arthwrntzch.SpringBootToDoList.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        uses = { TaskMapping.class },
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapping {

    // User -> UserDto
    UserDto toDto(User user);

    // UserDto -> User
    User toEntity(UserDto dto);
}
