package com.rsginer.springboottodolist.task.api;

import com.rsginer.springboottodolist.auth.domain.AppUserDetails;
import com.rsginer.springboottodolist.task.domain.Task;
import com.rsginer.springboottodolist.task.dto.CreateTaskDto;
import com.rsginer.springboottodolist.task.dto.mapper.TaskMapper;
import com.rsginer.springboottodolist.task.dto.TaskDto;
import com.rsginer.springboottodolist.task.service.TaskNotFoundException;
import com.rsginer.springboottodolist.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Tag(name = "Task")
@RestController
@RequestMapping("/api/tasks")
public class TaskRESTController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    @ResponseBody
    @Operation(security = @SecurityRequirement(name = "basicAuth"), description = "Get tasks for current user")
    @PageableAsQueryParam
    public Page<TaskDto> getTasks(@RequestParam(required = false) @Parameter(hidden = true) Pageable pageable, @AuthenticationPrincipal @Parameter(hidden = true) AppUserDetails appUserDetails) {
        return taskService.getTasks(appUserDetails.getUser(), pageable).map(Task::toDto);
    }

    @GetMapping("/{taskId}")
    @ResponseBody
    @Operation(security = @SecurityRequirement(name = "basicAuth"), description = "Get task by taskId")
    public TaskDto getTaskById(@PathVariable @Parameter(hidden = false) UUID taskId, @AuthenticationPrincipal @Parameter(hidden = true) AppUserDetails appUserDetails) throws TaskNotFoundException {
        return taskService.getById(appUserDetails.getUser(), taskId).orElseThrow(() -> new TaskNotFoundException(taskId)).toDto();
    }

    @PutMapping("/{taskId}")
    @ResponseBody
    @Operation(security = @SecurityRequirement(name = "basicAuth"), description = "Update task by taskId")
    public TaskDto updateTaskById(@PathVariable @Parameter(hidden = false) UUID taskId, @RequestBody(required=true) @Valid TaskDto task, @AuthenticationPrincipal @Parameter(hidden = true) AppUserDetails appUserDetails) throws TaskNotFoundException {
        return taskService.updateById(appUserDetails.getUser(), taskId, new TaskMapper().toEntity(task, taskId)).orElseThrow(() -> new TaskNotFoundException(taskId)).toDto();
    }

    @PatchMapping("/{taskId}/finish")
    @ResponseBody
    @Operation(security = @SecurityRequirement(name = "basicAuth"), description = "Finish task")
    public TaskDto finishTaskById(@PathVariable @Parameter UUID taskId, @AuthenticationPrincipal @Parameter(hidden = true) AppUserDetails appUserDetails) throws TaskNotFoundException {
        return taskService.finishById(appUserDetails.getUser(), taskId).orElseThrow(() -> new TaskNotFoundException(taskId)).toDto();
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"), description = "Create task and assign to user, by default current user is assigned")
    @PostMapping("/create")
    @ResponseBody
    public TaskDto create(@RequestBody(required = true) @Valid CreateTaskDto createTask,
                          @AuthenticationPrincipal @Parameter(hidden = true) AppUserDetails appUserDetails)  {
        var task = new TaskMapper().toEntity(createTask, appUserDetails.getUser());
        return this.taskService.createTask(appUserDetails.getUser(), task).toDto();
    }
}
