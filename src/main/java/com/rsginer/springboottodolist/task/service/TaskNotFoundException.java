package com.rsginer.springboottodolist.task.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends Exception {
    public TaskNotFoundException(UUID taskId) {
        super("Task with id " + taskId.toString() + " not found");
    }
}
