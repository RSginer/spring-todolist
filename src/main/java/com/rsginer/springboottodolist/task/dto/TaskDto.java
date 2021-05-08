package com.rsginer.springboottodolist.task.dto;

import com.rsginer.springboottodolist.task.TaskState;
import com.rsginer.springboottodolist.user.dto.AppUserDto;

import java.util.List;
import java.util.UUID;

public class TaskDto {
    private UUID id;
    private String description;
    private TaskState state;
    private AppUserDto createdBy;
    private List<AppUserDto> responsible;

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

    public List<AppUserDto> getResponsible() {
        return responsible;
    }

    public void setResponsible(List<AppUserDto> responsible) {
        this.responsible = responsible;
    }
}
