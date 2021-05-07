package com.rsginer.springboottodolist.domain.task;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Task {

    @Id
    private final UUID id = UUID.randomUUID();

    @Nullable
    private String description;

    private TaskState state;

    public UUID getId() {
        return id;
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
