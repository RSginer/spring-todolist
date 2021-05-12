package com.rsginer.springboottodolist.task.service;

import com.rsginer.springboottodolist.task.domain.Task;
import com.rsginer.springboottodolist.task.domain.TaskState;
import com.rsginer.springboottodolist.task.repository.TaskRepository;
import com.rsginer.springboottodolist.user.domain.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

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

    public Optional<Task> getById(AppUser user, UUID taskId) {
        var task = taskRepository.getTaskByIdAndIsCreatedByAppUserOrIsInAssignedTo(taskId, user);

        return Optional.ofNullable(task);
    }

    public Optional<Task> updateById(AppUser user, UUID taskId, Task task) {
        var foundTask = taskRepository.getTaskByIdAndIsCreatedByAppUserOrIsInAssignedTo(taskId, user);

        if (foundTask == null) {
            return Optional.empty();
        }

        foundTask.setAssignedTo(task.getAssignedTo());
        foundTask.setDescription(task.getDescription());

        var updatedTask = taskRepository.save(foundTask);

        return Optional.of(updatedTask);
    }

    public Optional<Task> finishById(AppUser user, UUID taskId) {
        var foundTask = taskRepository.getTaskByIdAndIsCreatedByAppUserOrIsInAssignedTo(taskId, user);

        if (foundTask == null) {
            return Optional.empty();
        }

        foundTask.setState(TaskState.FINISHED);
        var finishedTask = taskRepository.save(foundTask);

        return Optional.of(finishedTask);
    }
}
