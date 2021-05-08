package com.rsginer.springboottodolist.task.api;

import com.rsginer.springboottodolist.task.Task;
import com.rsginer.springboottodolist.task.dto.CreateTaskDto;
import com.rsginer.springboottodolist.task.dto.CreateTaskMapper;
import com.rsginer.springboottodolist.task.dto.TaskDto;
import com.rsginer.springboottodolist.task.service.TaskService;
import com.rsginer.springboottodolist.user.AppUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public Page<TaskDto> getTasks(@RequestParam(required = false) @ApiIgnore(
            "Ignored because swagger ui shows the wrong params, " +
                    "instead they are explained in the implicit params"
    ) Pageable pageable) {
        var currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication(). getPrincipal();

        return taskService.getTasks(currentUser, pageable).map(Task::toDto);
    }

    @ApiOperation(value = "Create task and assign responsible if needed (Optional) by default current user is responsible")
    @PostMapping("/create")
    @ResponseBody
    public TaskDto create(@RequestBody(required = true) CreateTaskDto createTask)  {
        var currentUser = (AppUser) SecurityContextHolder.getContext().getAuthentication(). getPrincipal();
        var task = new CreateTaskMapper().toEntity(createTask, currentUser);
        return this.taskService.createTask(currentUser, task).toDto();
    }
}
