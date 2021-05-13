package com.rsginer.springboottodolist.task.service;

import com.rsginer.springboottodolist.task.domain.ACLTask;
import com.rsginer.springboottodolist.task.domain.Task;
import com.rsginer.springboottodolist.task.api.TaskNotCreatedByAndNotAssignedToForbiddenException;
import com.rsginer.springboottodolist.task.domain.TaskState;
import com.rsginer.springboottodolist.task.repository.TaskRepository;
import com.rsginer.springboottodolist.user.domain.AppUser;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class TaskServiceImplTests {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private AppUser mockUser;

    private Task mockTask;

    private List<Task> mockTasks;

    @Before
    public void setup() {
        var user = new AppUser();
        user.setUsername("test@test.com");
        user.setPassword("Test");
        user.setFirstName("Test");
        user.setLastName("Test");
        mockUser = user;

        var createdTask = new Task();
        createdTask.setDescription("Test");
        createdTask.setCreatedBy(user);
        createdTask.setAssignedTo(Collections.singletonList(user));
        mockTask = createdTask;


        Task task1 = new Task();
        task1.setCreatedBy(mockUser);
        task1.setAssignedTo(Collections.singletonList(mockUser));
        task1.setDescription("Test 1");

        Task task2 = new Task();
        task2.setCreatedBy(mockUser);
        task2.setAssignedTo(Collections.singletonList(mockUser));
        task2.setDescription("Test 2");
        mockTasks = Arrays.asList(task1, task2);
    }

    @Test
    public void shouldCreateATaskAndSetCreatedByAndAssignToUser() {
        System.out.println(mockTask.getId());
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        var task =  new Task();
        task.setId(mockTask.getId());
        task.setDescription(mockTask.getDescription());

        var taskToTest = this.taskService.createTask(mockUser, task);

        assertThat(taskToTest.getDescription()).isSameAs(task.getDescription());
        assertThat(taskToTest.getCreatedBy().getId()).isSameAs(mockUser.getId());
        assertThat(taskToTest.getAssignedTo().get(0)).isSameAs(mockUser);
    }

    @Test
    public void shouldReturnTheTasksForGivenUser() {
        when(taskRepository.findByAssignedTo(any(AppUser.class), eq(null)))
                .thenReturn(
                        new PageImpl<>(mockTasks)
                );

        var page = taskService.getTasks(mockUser, null);

        assertThat(page.toList().get(0).getId()).isSameAs(mockTasks.get(0).getId());
        assertThat(page.toList().get(0).getAssignedTo().indexOf(mockUser)).isNotNegative();

        assertThat(page.toList().get(1).getId()).isSameAs(mockTasks.get(1).getId());
        assertThat(page.toList().get(1).getAssignedTo().indexOf(mockUser)).isNotNegative();

        verify(taskRepository).findByAssignedTo(mockUser, null);
    }

    @Test
    public void shouldReturnTaskByIdForGivenUser() throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        when(taskRepository.findById(mockTask.getId()))
                .thenReturn(Optional.of(mockTask));

        var foundTask = taskService.getById(mockUser, mockTask.getId());

        assertThat(foundTask).isPresent();
        assertThat(foundTask.get().getId()).isSameAs(mockTask.getId());
        assertThat(foundTask.get().getAssignedTo().get(0).getId()).isSameAs(mockUser.getId());
        verify(taskRepository).findById(mockTask.getId());
    }

    @Test
    public void shouldUpdateTaskById() throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        when(taskRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(mockTask));
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        var updatedTask = taskService.updateById(mockUser, mockTask.getId(), mockTask);

        assertThat(updatedTask).isPresent();
        assertThat(updatedTask.get().getId()).isSameAs(mockTask.getId());
        verify(taskRepository).save(mockTask);
        verify(taskRepository).findById(mockTask.getId());
    }

    @Test
    public void shouldReturnEmptyUpdateInvalidTask() throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        when(taskRepository.findById(any(UUID.class)))
                .thenReturn(Optional.empty());

        var updatedTask = taskService.updateById(mockUser, mockTask.getId(), mockTask);

        assertThat(updatedTask).isEmpty();
        verify(taskRepository).findById(mockTask.getId());
    }

    @Test
    public void shouldReturnEmptyGetByIdInvalidTask() throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        when(taskRepository.findById(any(UUID.class)))
                .thenReturn(Optional.empty());

        var updatedTask = taskService.getById(mockUser, mockTask.getId());

        assertThat(updatedTask).isEmpty();
        verify(taskRepository).findById(mockTask.getId());
    }

    @Test
    public void shouldFinishATask() throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        when(taskRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(mockTask));
        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

        var updatedTask = taskService.finishById(mockUser, mockTask.getId());

        assertThat(updatedTask).isPresent();
        assertThat(updatedTask.get().getState()).isSameAs(TaskState.FINISHED);
        verify(taskRepository).findById(mockTask.getId());
        verify(taskRepository).save(mockTask);
    }

    @Test
    public void shouldReturnEmptyFinishTask() throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        when(taskRepository.findById(any(UUID.class)))
                .thenReturn(Optional.empty());

        var updatedTask = taskService.finishById(mockUser, mockTask.getId());

        assertThat(updatedTask).isEmpty();
        verify(taskRepository).findById(mockTask.getId());
    }


    @Test(expected = TaskNotCreatedByAndNotAssignedToForbiddenException.class)
    public void shouldThrowForbiddenUpdateTask() throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        var hacker = new AppUser();
        hacker.setUsername("Hacker");
        hacker.setPassword("Hacker");
        hacker.setFirstName("Hacker");
        hacker.setLastName("Hacker");

        when(taskRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(mockTask));

        taskService.updateById(hacker, mockTask.getId(), mockTask);

        verify(taskRepository).findById(mockTask.getId());
    }


    @Test(expected = TaskNotCreatedByAndNotAssignedToForbiddenException.class)
    public void shouldThrowForbiddenFinishTask() throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        var hacker = new AppUser();
        hacker.setUsername("Hacker");
        hacker.setPassword("Hacker");
        hacker.setFirstName("Hacker");
        hacker.setLastName("Hacker");

        when(taskRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(mockTask));

        taskService.finishById(hacker, mockTask.getId());

        verify(taskRepository).findById(mockTask.getId());
    }


    @Test(expected = TaskNotCreatedByAndNotAssignedToForbiddenException.class)
    public void shouldThrowForbiddenGetTaskById() throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        var hacker = new AppUser();
        hacker.setUsername("Hacker");
        hacker.setPassword("Hacker");
        hacker.setFirstName("Hacker");
        hacker.setLastName("Hacker");

        when(taskRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(mockTask));

        taskService.getById(hacker, mockTask.getId());

        verify(taskRepository).findById(mockTask.getId());
    }

    @Test(expected = TaskNotCreatedByAndNotAssignedToForbiddenException.class)
    public void shouldThrowForbiddenDeleteTaskById() throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        var hacker = new AppUser();
        hacker.setUsername("Hacker");
        hacker.setPassword("Hacker");
        hacker.setFirstName("Hacker");
        hacker.setLastName("Hacker");

        when(taskRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(mockTask));

        taskService.deleteTaskById(hacker, mockTask.getId());

        verify(taskRepository).findById(mockTask.getId());
    }

    @Test
    public void shouldDeleteTaskById() throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        when(taskRepository.findById(any(UUID.class))).thenReturn(Optional.of(mockTask));

        var result = taskService.deleteTaskById(mockUser, mockTask.getId());
        verify(taskRepository).findById(mockTask.getId());
        verify(taskRepository).deleteById(mockTask.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isTrue();
    }

    @Test
    public void shouldReturnEmptyDeleteTaskById() throws TaskNotCreatedByAndNotAssignedToForbiddenException {
        when(taskRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        var result = taskService.deleteTaskById(mockUser, mockTask.getId());

        verify(taskRepository).findById(mockTask.getId());
        assertThat(result).isEmpty();
    }

}
