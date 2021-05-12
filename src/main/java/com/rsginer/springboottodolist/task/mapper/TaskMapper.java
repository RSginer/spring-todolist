package com.rsginer.springboottodolist.task.mapper;

import com.rsginer.springboottodolist.task.domain.Task;
import com.rsginer.springboottodolist.task.dto.CreateTaskDto;
import com.rsginer.springboottodolist.task.dto.TaskDto;
import com.rsginer.springboottodolist.user.domain.AppUser;
import com.rsginer.springboottodolist.user.mapper.AppUserMapper;

import java.util.UUID;
import java.util.stream.Collectors;

public class TaskMapper {
    public Task toEntity(CreateTaskDto createTaskDto, AppUser appUser) {
        var task = new Task(appUser);
        task.setDescription(createTaskDto.getDescription());

        if (createTaskDto.getAsignedTo() != null) {
            var uuidAssignedTo = createTaskDto.getAsignedTo();
            var assignedTo = uuidAssignedTo.stream().map(AppUser::new)
                    .collect(Collectors.toList());
            assignedTo.add(appUser);
            task.setAssignedTo(assignedTo);
        }

        return task;
    }

    public Task toEntity(TaskDto taskDto, UUID taskId) {
        var task = new Task(taskId);
        task.setDescription(taskDto.getDescription());

        if (taskDto.getAssignedTo() != null) {
            var appUserDtoList = taskDto.getAssignedTo();
            var assignedTo = appUserDtoList.stream().map(appUserDto -> new AppUserMapper().toEntity(appUserDto))
                    .collect(Collectors.toList());
            task.setAssignedTo(assignedTo);
        }

        return task;
    }
}
