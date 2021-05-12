package com.rsginer.springboottodolist.task.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TaskNotCreatedByAndNotAssignedToForbidden extends Exception{
    public TaskNotCreatedByAndNotAssignedToForbidden(UUID taskId) {
        super("Forbidden access to task: " + taskId.toString());
    }
}
