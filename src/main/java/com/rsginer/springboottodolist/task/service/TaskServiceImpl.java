package com.rsginer.springboottodolist.task.service;

import com.rsginer.springboottodolist.task.domain.Task;
import com.rsginer.springboottodolist.task.repository.TaskRepository;
import com.rsginer.springboottodolist.user.domain.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Page<Task> getTasks(AppUser user, Pageable pageable) {
        return this.taskRepository.findByAsignedTo(user, pageable);
    }

    public Task createTask(AppUser user, Task task) {
        task.setCreatedBy(user);
        var asignedTo = task.getAsignedTo();
        asignedTo.add(user);
        task.setAsignedTo(asignedTo);

        return this.taskRepository.save(task);
    }
}
