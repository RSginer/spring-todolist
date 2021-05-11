package com.rsginer.springboottodolist.task.dto;

import com.rsginer.springboottodolist.task.domain.Task;
import com.rsginer.springboottodolist.user.domain.AppUser;

import java.util.stream.Collectors;

public class CreateTaskMapper {
    public Task toEntity(CreateTaskDto createTaskDto, AppUser appUser) {
        var task = new Task(appUser);
        task.setDescription(createTaskDto.getDescription());

        if (createTaskDto.getAsignedTo() != null) {
            var uuidAsignedTo = createTaskDto.getAsignedTo();
            var asignedTo = uuidAsignedTo.stream().map(AppUser::new)
                    .collect(Collectors.toList());
            asignedTo.add(appUser);
            task.setAsignedTo(asignedTo);
        }

        return task;
    }
}
