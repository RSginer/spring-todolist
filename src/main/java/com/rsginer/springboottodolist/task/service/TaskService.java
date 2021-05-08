package com.rsginer.springboottodolist.task.service;

import com.rsginer.springboottodolist.task.Task;
import com.rsginer.springboottodolist.user.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Page<Task> getTasks(AppUser user, Pageable pageable);
    Task createTask(AppUser user, Task task);
}
