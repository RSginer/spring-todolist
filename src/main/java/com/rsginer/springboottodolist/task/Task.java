package com.rsginer.springboottodolist.task;

import com.rsginer.springboottodolist.user.AppUser;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Task {

    @Id
    private UUID id;

    @NotBlank(message = "Description is required")
    private String description;

    private TaskState state;

    @ManyToOne
    private AppUser createdBy;

    @ManyToMany
    private List<AppUser> responsible;

    public Task() {
        this.id = UUID.randomUUID();
        this.responsible = new ArrayList<>();
    }

    public Task(AppUser createdBy) {
        this.id = UUID.randomUUID();
        this.createdBy = createdBy;
        this.responsible = new ArrayList<>();
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

    public List<AppUser> getResponsible() {
        return responsible;
    }

    public void setResponsible(List<AppUser> responsible) {
        this.responsible = responsible;
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
}
