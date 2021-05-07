package com.rsginer.springboottodolist.task.service;

import com.rsginer.springboottodolist.task.Task;
import com.rsginer.springboottodolist.task.repository.TaskRepository;
import com.rsginer.springboottodolist.user.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Page<Task> getTasks(AppUser user, Pageable pageable) {
        return this.taskRepository.findByResponsible(user, pageable);
    }
}
