package com.rsginer.springboottodolist.task.service;

import com.rsginer.springboottodolist.task.domain.Task;
import com.rsginer.springboottodolist.task.api.TaskNotCreatedByAndNotAssignedToForbiddenException;
import com.rsginer.springboottodolist.task.domain.TaskState;
import com.rsginer.springboottodolist.task.repository.TaskRepository;
import com.rsginer.springboottodolist.user.domain.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private boolean isUserAuthorized(AppUser user, Task task) {
        var assignedToUuids = task.getAssignedTo().stream().map(AppUser::getUsername).collect(Collectors.toList());

        return task.getCreatedBy().getUsername().equals(user.getUsername()) || assignedToUuids.contains(user.getId().toString());
    }

    public Page<Task> getTasks(AppUser user, Pageable pageable) {
        return this.taskRepository.findByAssignedTo(user, pageable);
    }

    public Task createTask(AppUser user, Task task) {
        task.setCreatedBy(user);
        var assignedTo = task.getAssignedTo();
        assignedTo.add(user);
        task.setAssignedTo(assignedTo);

        return this.taskRepository.save(task);
    }

    public Optional<Task> getById(AppUser user, UUID taskId) throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        var task = taskRepository.findById(taskId);

        if (task.isPresent()) {
            if (isUserAuthorized(user, task.get())) {
                return task;
            }

            throw new TaskNotCreatedByAndNotAssignedToForbiddenException(task.get().getId());
        }

        return Optional.empty();
    }

    public Optional<Task> updateById(AppUser user, UUID taskId, Task task) throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        var foundTask = taskRepository.findById(taskId);

        if (foundTask.isPresent()) {
            if (isUserAuthorized(user, foundTask.get())) {
                foundTask.get().setDescription(task.getDescription());

                if (!task.getAssignedTo().isEmpty()) {
                    foundTask.get().setAssignedTo(task.getAssignedTo());
                }

                var updatedTask = taskRepository.save(foundTask.get());

                return Optional.of(updatedTask);
            }

            throw new TaskNotCreatedByAndNotAssignedToForbiddenException(foundTask.get().getId());
        }

        return Optional.empty();
    }

    public Optional<Task> finishById(AppUser user, UUID taskId) throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        var foundTask = taskRepository.findById(taskId);

        if (foundTask.isPresent()) {

            if (isUserAuthorized(user, foundTask.get())) {
                foundTask.get().setState(TaskState.FINISHED);
                var finishedTask = taskRepository.save(foundTask.get());

                return Optional.of(finishedTask);
            }

            throw new TaskNotCreatedByAndNotAssignedToForbiddenException(foundTask.get().getId());
        }

        return Optional.empty();
    }

    public Optional<Boolean> deleteTaskById(AppUser user, UUID taskId) throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        var task = taskRepository.findById(taskId);

        if (task.isPresent()) {

            if (isUserAuthorized(user, task.get())) {
                taskRepository.deleteById(taskId);
                return Optional.of(true);
            }

            throw new TaskNotCreatedByAndNotAssignedToForbiddenException(taskId);
        }

        return Optional.of(false);
    }
}
