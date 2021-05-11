package com.rsginer.springboottodolist.task.service;

import com.rsginer.springboottodolist.task.domain.Task;
import com.rsginer.springboottodolist.task.repository.TaskRepository;
import com.rsginer.springboottodolist.user.domain.AppUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void shouldCreateATaskAndSetCreatedByAndAssignToUser() {
        var user = new AppUser();
        user.setUsername("test@test.com");
        user.setPassword("Test");
        user.setFirstName("Test");
        user.setLastName("Test");

        var createdTask = new Task();
        createdTask.setDescription("Test");
        createdTask.setCreatedBy(user);
        createdTask.setAssignedTo(Collections.singletonList(user));

        when(taskRepository.save(any(Task.class))).thenReturn(createdTask);

        var task =  new Task();
        task.setId(createdTask.getId());
        task.setDescription(createdTask.getDescription());

        var taskToTest = this.taskService.createTask(user, task);

        assertThat(taskToTest.getDescription()).isSameAs(task.getDescription());
        assertThat(taskToTest.getCreatedBy().getId()).isSameAs(user.getId());
        assertThat(taskToTest.getAssignedTo().get(0)).isSameAs(user);
    }

}
