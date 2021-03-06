package com.rsginer.springboottodolist.task.service;

import com.rsginer.springboottodolist.task.domain.Task;
import com.rsginer.springboottodolist.task.api.TaskNotCreatedByAndNotAssignedToForbiddenException;
import com.rsginer.springboottodolist.user.domain.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    Page<Task> getTasks(AppUser user, Pageable pageable);
    Task createTask(AppUser user, Task task);
    Optional<Task> getById(AppUser user, UUID taskId) throws TaskNotCreatedByAndNotAssignedToForbiddenException;
    Optional<Task> updateById(AppUser user, UUID taskId, Task task) throws TaskNotCreatedByAndNotAssignedToForbiddenException;
    Optional<Task> finishById(AppUser user, UUID taskId) throws TaskNotCreatedByAndNotAssignedToForbiddenException;
    Optional<Boolean> deleteTaskById(AppUser user, UUID taskId) throws TaskNotCreatedByAndNotAssignedToForbiddenException;
}
