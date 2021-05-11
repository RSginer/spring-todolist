package com.rsginer.springboottodolist.task.api;

import com.rsginer.springboottodolist.auth.domain.AppUserDetails;
import com.rsginer.springboottodolist.task.domain.Task;
import com.rsginer.springboottodolist.task.dto.CreateTaskDto;
import com.rsginer.springboottodolist.task.mapper.CreateTaskMapper;
import com.rsginer.springboottodolist.task.dto.TaskDto;
import com.rsginer.springboottodolist.task.service.TaskService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Tasks")
@RestController
@RequestMapping("/api/tasks")
public class TaskRESTController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    @ResponseBody
    @Operation(security = @SecurityRequirement(name = "basicAuth"), description = "Get tasks for current user")
    @PageableAsQueryParam
    public Page<TaskDto> getTasks(@RequestParam(required = false) @Parameter(hidden = false) Pageable pageable, @AuthenticationPrincipal @Parameter(hidden = true) AppUserDetails appUserDetails) {
        return taskService.getTasks(appUserDetails.getUser(), pageable).map(Task::toDto);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"), description = "Create task and assign to user, by default current user is assigned")
    @PostMapping("/create")
    @ResponseBody
    public TaskDto create(@RequestBody(required = true) CreateTaskDto createTask,
                          @AuthenticationPrincipal @Parameter(hidden = true) AppUserDetails appUserDetails)  {
        var task = new CreateTaskMapper().toEntity(createTask, appUserDetails.getUser());
        return this.taskService.createTask(appUserDetails.getUser(), task).toDto();
    }
}
