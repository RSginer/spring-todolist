package com.rsginer.springboottodolist.task.dto;

import com.rsginer.springboottodolist.task.Task;
import com.rsginer.springboottodolist.user.AppUser;

import java.util.stream.Collectors;

public class CreateTaskMapper {
    public Task toEntity(CreateTaskDto createTaskDto, AppUser appUser) {
        var task = new Task(appUser);
        task.setDescription(createTaskDto.getDescription());

        if (createTaskDto.getResponsible() != null) {
            var uuidResponsible = createTaskDto.getResponsible();
            var responsible = uuidResponsible.stream().map(AppUser::new)
                    .collect(Collectors.toList());
            responsible.add(appUser);
            task.setResponsible(responsible);
        }

        return task;
    }
}
