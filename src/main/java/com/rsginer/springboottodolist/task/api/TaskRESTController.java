package com.rsginer.springboottodolist.task.api;

import com.rsginer.springboottodolist.task.Task;
import com.rsginer.springboottodolist.task.service.TaskService;
import com.rsginer.springboottodolist.user.AppUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Api(value = "Tasks", tags = "Tasks")
@RestController
@RequestMapping("/api/tasks")
public class TaskRESTController {

    @Autowired
    private TaskService taskService;

    @ApiOperation(value = "Get tasks for current user")
    @GetMapping
    @ResponseBody
    public Page<Task> getTasks(@RequestParam(required = false) Pageable pageable, AppUser currentUserDetails) {
        return taskService.getTasks(currentUserDetails, pageable);
    }
}
