package com.rsginer.springboottodolist.task.domain;

import com.rsginer.springboottodolist.task.dto.TaskDto;
import com.rsginer.springboottodolist.user.domain.AppUser;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
public class Task {

    @Id
    private UUID id;

    @NotBlank(message = "Description is required")
    private String description;

    private TaskState state = TaskState.TODO;

    @ManyToOne
    private AppUser createdBy;

    @ManyToMany
    private List<AppUser> assignedTo;

    public Task() {
        this.id = UUID.randomUUID();
        this.assignedTo = new ArrayList<>();
    }

    public Task(AppUser createdBy) {
        this.id = UUID.randomUUID();
        this.createdBy = createdBy;
        this.assignedTo = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AppUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AppUser createdBy) {
        this.createdBy = createdBy;
    }

    public List<AppUser> getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(List<AppUser> asignedTo) {
        this.assignedTo = asignedTo;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public TaskDto toDto() {
        var dto = new TaskDto();
        dto.setId(this.getId());
        dto.setAssignedTo(
                this.getAssignedTo()
                        .stream()
                        .map(AppUser::toDto)
                        .collect(Collectors.toList())
        );
        dto.setCreatedBy(this.getCreatedBy().toDto());
        dto.setState(this.getState());
        dto.setDescription(this.getDescription());

        return dto;
    }
}
