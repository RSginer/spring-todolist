package com.rsginer.springboottodolist.task.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TaskNotCreatedByAndNotAssignedToForbiddenException extends Exception{
    public TaskNotCreatedByAndNotAssignedToForbiddenException(UUID taskId) {
        super("Forbidden access denied to task: " + taskId.toString());
    }
}
