package com.devtiro.task.service;

import com.devtiro.task.domain.CreateTaskRequest;
import com.devtiro.task.domain.entity.Task;

public interface TaskService {

    Task createTask(CreateTaskRequest request);

}
