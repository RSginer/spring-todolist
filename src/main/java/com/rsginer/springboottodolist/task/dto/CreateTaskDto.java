package com.rsginer.springboottodolist.task.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(name = "CreateTask")
public class CreateTaskDto {
    private String description;
    private List<UUID> asignedTo;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UUID> getAsignedTo() {
        return asignedTo;
    }

    public void setAsignedTo(List<UUID> asignedTo) {
        this.asignedTo = asignedTo;
    }
}
