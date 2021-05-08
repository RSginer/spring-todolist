package com.rsginer.springboottodolist.task.api;

import com.rsginer.springboottodolist.task.Task;
import com.rsginer.springboottodolist.task.service.TaskService;
import com.rsginer.springboottodolist.task.service.TaskServiceImpl;
import com.rsginer.springboottodolist.user.AppUser;
import com.rsginer.springboottodolist.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/api/task")
public class TaskRESTController {


    @Autowired
    private TaskService taskService;

    @GetMapping("/get")
    @ResponseBody
    public Page<Task> getTasks(@RequestParam(required = false) Pageable pageable, AppUser appUser) {
        return taskService.getTasks(appUser, pageable);
    }
}
