package com.devtiro.task.service.impl;
import com.devtiro.task.domain.CreateTaskRequest;
import com.devtiro.task.domain.entity.Task;
import com.devtiro.task.domain.entity.TaskStatus;
import com.devtiro.task.repositoty.TaskRepository;
import com.devtiro.task.service.TaskService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(CreateTaskRequest request) {
        Instant now = Instant.now();

        Task task = new Task(
                null,
                request.title(),
                request.description(),
                request.dueDate(),
                TaskStatus.OPEN,
                request.priority(),
                now,
                now
        );

        return taskRepository.save(task);
    }

    @Override
    public List<Task> listTask() {
       return taskRepository.findAll(Sort.by(Sort.Direction.ASC, "created"));
    }
}
