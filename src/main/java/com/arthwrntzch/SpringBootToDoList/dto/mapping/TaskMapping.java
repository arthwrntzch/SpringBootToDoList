package com.arthwrntzch.SpringBootToDoList.dto.mapping;

import com.arthwrntzch.SpringBootToDoList.dto.TaskDto;
import com.arthwrntzch.SpringBootToDoList.entity.Task;
import com.arthwrntzch.SpringBootToDoList.enums.TaskStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = { TaskStatus.class }
)
public interface TaskMapping {

    // Task -> TaskDto
    @Mapping(target = "status", expression = "java(task.getStatus() != null ? task.getStatus().name() : null)")
    @Mapping(target = "userId", source = "user.id")
    TaskDto toDto(Task task);

    // TaskDto -> Task (user выставляем в сервисе по dto.getUserId())
    @Mapping(target = "status", expression = "java(dto.getStatus() != null ? TaskStatus.valueOf(dto.getStatus().trim().toUpperCase()) : null)")
    @Mapping(target = "user", ignore = true)
    Task toEntity(TaskDto dto);
}
