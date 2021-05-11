package com.rsginer.springboottodolist.task.dto;

import com.rsginer.springboottodolist.task.domain.TaskState;
import com.rsginer.springboottodolist.user.dto.AppUserDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(name = "Task")
public class TaskDto {
    private UUID id;
    private String description;
    private TaskState state;
    private AppUserDto createdBy;
    private List<AppUserDto> asignedTo;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public AppUserDto getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AppUserDto createdBy) {
        this.createdBy = createdBy;
    }

    public List<AppUserDto> getAsignedTo() {
        return asignedTo;
    }

    public void setAsignedTo(List<AppUserDto> asignedTo) {
        this.asignedTo = asignedTo;
    }
}
