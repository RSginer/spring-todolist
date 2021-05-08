package com.rsginer.springboottodolist.task.api;

import com.rsginer.springboottodolist.task.Task;
import com.rsginer.springboottodolist.task.service.TaskService;
import com.rsginer.springboottodolist.user.security.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskRESTController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    @ResponseBody
    public Page<Task> getTasks(@RequestParam(required = false) Pageable pageable, AppUserDetails currentUserDetails) {
        return taskService.getTasks(currentUserDetails.getUser(), pageable);
    }
}
