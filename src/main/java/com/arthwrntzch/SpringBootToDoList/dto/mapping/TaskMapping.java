package com.arthwrntzch.SpringBootToDoList.dto.mapping;

import com.arthwrntzch.SpringBootToDoList.dto.TaskDto;
import com.arthwrntzch.SpringBootToDoList.entity.Task;
import com.arthwrntzch.SpringBootToDoList.enums.TaskStatus;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = { TaskStatus.class })
public interface TaskMapping {

    @Mapping(target = "status", expression = "java(task.getStatus() != null ? task.getStatus().name() : null)")
    @Mapping(target = "userId", source = "user.id")
    TaskDto toDto(Task task);

    @Mapping(target = "status", expression = "java(dto.getStatus() != null ? TaskStatus.valueOf(dto.getStatus().trim().toUpperCase()) : null)")
    @Mapping(target = "user", ignore = true)
    Task toEntity(TaskDto dto);
}
