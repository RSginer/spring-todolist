package com.rsginer.springboottodolist.task;

import com.rsginer.springboottodolist.user.AppUser;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;
import java.util.UUID;

@Entity
public class Task {

    @Id
    private final UUID id = UUID.randomUUID();

    @Nullable
    private String description;

    private TaskState state;

    @ManyToOne
    private AppUser createdBy;

    @ManyToMany
    private List<AppUser> responsible;

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
