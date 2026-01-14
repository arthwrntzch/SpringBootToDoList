package com.arthwrntzch.SpringBootToDoList.dto.mapping;

import com.arthwrntzch.SpringBootToDoList.dto.TaskDto;
import com.arthwrntzch.SpringBootToDoList.entity.Task;
import com.arthwrntzch.SpringBootToDoList.enums.TaskStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
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
    @Mapping(target = "status", source = "status", qualifiedByName = "toTaskStatus")
    @Mapping(target = "user", ignore = true)
    Task toEntity(TaskDto dto);

    @Named("toTaskStatus")
    default TaskStatus toTaskStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return null;
        }
        try {
            return TaskStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
