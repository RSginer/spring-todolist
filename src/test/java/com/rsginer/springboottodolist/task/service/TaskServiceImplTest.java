package com.rsginer.springboottodolist.task.service;

import com.rsginer.springboottodolist.task.domain.Task;
import com.rsginer.springboottodolist.task.repository.TaskRepository;
import com.rsginer.springboottodolist.user.domain.AppUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
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

    @Test
    public void shouldReturnTheTasksForGivenUser() {
        var user = new AppUser();
        user.setUsername("test@test.com");
        user.setPassword("Test");
        user.setFirstName("Test");
        user.setLastName("Test");

        Task task1 = new Task();
        task1.setCreatedBy(user);
        task1.setAssignedTo(Collections.singletonList(user));
        task1.setDescription("Test 1");

        Task task2 = new Task();
        task2.setCreatedBy(user);
        task2.setAssignedTo(Collections.singletonList(user));
        task2.setDescription("Test 2");

        when(taskRepository.findByAssignedTo(any(AppUser.class), eq(null)))
                .thenReturn(
                        new PageImpl<>(Arrays.asList(task1, task2))
                );

        var page = taskService.getTasks(user, null);

        assertThat(page.toList().get(0).getId()).isSameAs(task1.getId());
        assertThat(page.toList().get(0).getAssignedTo().indexOf(user)).isNotNegative();

        assertThat(page.toList().get(1).getId()).isSameAs(task2.getId());
        assertThat(page.toList().get(1).getAssignedTo().indexOf(user)).isNotNegative();

        verify(taskRepository).findByAssignedTo(user, null);
    }

}
