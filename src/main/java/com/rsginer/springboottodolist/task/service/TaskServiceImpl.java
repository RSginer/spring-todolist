package com.rsginer.springboottodolist.task.service;

import com.rsginer.springboottodolist.task.domain.ACLTask;
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

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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

        if (task.isPresent() && ACLTask.isUserAuthorized(user, task.get())) {
            return task;
        }

        return Optional.empty();
    }

    public Optional<Task> updateById(AppUser user, UUID taskId, Task task) throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        var foundTask = taskRepository.findById(taskId);

        if (foundTask.isPresent() && ACLTask.isUserAuthorized(user, foundTask.get())) {
            if (!task.getDescription().equals("")) {
                foundTask.get().setDescription(task.getDescription());
            }
            if (task.getState() != null) {
                foundTask.get().setState(task.getState());
            }
            if (!task.getAssignedTo().isEmpty()) {
                foundTask.get().setAssignedTo(task.getAssignedTo());
            }

            var updatedTask = taskRepository.save(foundTask.get());

            return Optional.of(updatedTask);
        }

        return Optional.empty();
    }

    public Optional<Task> finishById(AppUser user, UUID taskId) throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        var foundTask = taskRepository.findById(taskId);

        if (foundTask.isPresent() && ACLTask.isUserAuthorized(user, foundTask.get())) {
            foundTask.get().setState(TaskState.FINISHED);
            var finishedTask = taskRepository.save(foundTask.get());

            return Optional.of(finishedTask);
        }

        return Optional.empty();
    }

    public Optional<Boolean> deleteTaskById(AppUser user, UUID taskId) throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        var task = taskRepository.findById(taskId);

        if (task.isPresent() && ACLTask.isUserAuthorized(user, task.get())) {
            taskRepository.deleteById(taskId);
            return Optional.of(true);
        }

        return Optional.empty();
    }
}
