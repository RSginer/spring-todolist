package com.rsginer.springboottodolist.task.dto.mapper;

import com.rsginer.springboottodolist.task.domain.Task;
import com.rsginer.springboottodolist.task.dto.CreateTaskDto;
import com.rsginer.springboottodolist.user.domain.AppUser;

import java.util.stream.Collectors;

public class TaskMapper {
    public Task toEntity(CreateTaskDto createTaskDto, AppUser appUser) {
        var task = new Task(appUser);
        task.setDescription(createTaskDto.getDescription());

        if (createTaskDto.getAssignedTo() != null) {
            var uuidAssignedTo = createTaskDto.getAssignedTo();
            var assignedTo = uuidAssignedTo.stream().map(AppUser::new)
                    .collect(Collectors.toList());
            assignedTo.add(appUser);
            task.setAssignedTo(assignedTo);
        }

        return task;
    }
}
