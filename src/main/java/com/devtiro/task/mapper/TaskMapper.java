package com.devtiro.task.mapper;

import com.devtiro.task.domain.CreateTaskRequest;
import com.devtiro.task.domain.dto.TaskDto;
import com.devtiro.task.domain.entity.Task;

public interface TaskMapper {

    CreateTaskRequest fromDto(CreateTaskRequest dto);

    TaskDto toDto(Task task);
}
