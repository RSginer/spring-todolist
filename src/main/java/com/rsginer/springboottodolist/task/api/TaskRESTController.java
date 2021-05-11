package com.rsginer.springboottodolist.task.api;

import com.rsginer.springboottodolist.security.auth.AppUserDetails;
import com.rsginer.springboottodolist.task.Task;
import com.rsginer.springboottodolist.task.dto.CreateTaskDto;
import com.rsginer.springboottodolist.task.dto.CreateTaskMapper;
import com.rsginer.springboottodolist.task.dto.TaskDto;
import com.rsginer.springboottodolist.task.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "Tasks", tags = "Tasks")
@RestController
@RequestMapping("/api/tasks")
public class TaskRESTController {
    @Autowired
    private TaskService taskService;

    @ApiOperation(value = "Get tasks for current user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.", defaultValue = "5"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    @GetMapping
    @ResponseBody
    public Page<TaskDto> getTasks(@RequestParam(required = false) @ApiIgnore Pageable pageable, @ApiIgnore @AuthenticationPrincipal AppUserDetails appUserDetails) {
        System.out.println(appUserDetails);
        System.out.println(pageable);
        var page = taskService.getTasks(appUserDetails.getUser(), pageable);

        return page.map(Task::toDto);

        // return taskService.getTasks(appUserDetails.getUser(), pageable).map(Task::toDto);
    }

    @ApiOperation(value = "Create task and assign to users if needed (Optional) by default current user is assigned")
    @PostMapping("/create")
    @ResponseBody
    public TaskDto create(@RequestBody(required = true) CreateTaskDto createTask,
                          @ApiIgnore @AuthenticationPrincipal AppUserDetails appUserDetails)  {
        var task = new CreateTaskMapper().toEntity(createTask, appUserDetails.getUser());
        return this.taskService.createTask(appUserDetails.getUser(), task).toDto();
    }
}
