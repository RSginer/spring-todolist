package com.rsginer.springboottodolist.task.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Schema(name = "CreateTask")
public class CreateTaskDto {

    @NotBlank(message = "Description is required")
    private String description;

    private List<UUID> assignedTo;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UUID> getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(List<UUID> assignedTo) {
        this.assignedTo = assignedTo;
    }
}
