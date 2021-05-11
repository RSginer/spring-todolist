package com.rsginer.springboottodolist.task.service;

import com.rsginer.springboottodolist.task.domain.Task;
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
}
