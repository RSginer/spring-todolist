package com.rsginer.springboottodolist.task.repository;

import com.rsginer.springboottodolist.task.domain.Task;
import com.rsginer.springboottodolist.user.domain.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    Page<Task> findByAssignedTo(AppUser user, Pageable pageable);

    @Query("select t from Task t join t.assignedTo assignedTo where t.id = ?1 and (t.createdBy = ?2 or assignedTo = ?2)")
    Task getTaskByIdAndIsCreatedByAppUserOrIsInAssignedTo(UUID taskId, AppUser user);
}
