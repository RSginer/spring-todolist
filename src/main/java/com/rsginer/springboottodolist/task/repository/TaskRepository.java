package com.rsginer.springboottodolist.task.repository;

import com.rsginer.springboottodolist.task.domain.Task;
import com.rsginer.springboottodolist.user.domain.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    Page<Task> findByAsignedTo(AppUser user, Pageable pageable);
}
