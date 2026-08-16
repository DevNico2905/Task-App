package com.devtiro.task.service;

import com.devtiro.task.domain.CreateTaskRequest;
import com.devtiro.task.domain.entity.Task;

import java.util.List;

public interface TaskService {

    Task createTask(CreateTaskRequest request);

    List<Task> listTask();

}
