package com.rsginer.springboottodolist.task.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsginer.springboottodolist.security.MockSecurityRESTController;
import com.rsginer.springboottodolist.security.WithMockAppUser;
import com.rsginer.springboottodolist.auth.domain.AppUserDetails;
import com.rsginer.springboottodolist.task.domain.Task;
import com.rsginer.springboottodolist.task.domain.TaskState;
import com.rsginer.springboottodolist.task.dto.CreateTaskDto;
import com.rsginer.springboottodolist.task.service.TaskService;
import com.rsginer.springboottodolist.user.domain.AppUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskRESTController.class)
@EnableSpringDataWebSupport
public class TaskRESTControllerTests extends MockSecurityRESTController {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @Test
    @WithMockAppUser
    public void shouldReturnTaskForTestUser() throws Exception {
        var userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userDetails.getUser();

        Task task1 = new Task();
        task1.setCreatedBy(user);
        task1.setAssignedTo(Collections.singletonList(user));
        task1.setDescription("Test 1");

        Task task2 = new Task();
        task2.setCreatedBy(user);
        task1.setAssignedTo(Collections.singletonList(user));
        task2.setDescription("Test 2");

        Page<Task> page = new PageImpl<>(Arrays.asList(task1, task2));

        when(taskService.getTasks(any(AppUser.class), eq(null))).thenReturn(page);

        this.mockMvc.perform(get("/api/tasks"))
                .andDo(print())
                .andExpect(status()
                        .isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].description")
                        .value(task1.getDescription()))
                .andExpect(jsonPath("$.content[1].description")
                        .value(task2.getDescription()));

        verify(taskService).getTasks(user, null);
    }

    @Test
    @WithMockAppUser
    public void shouldCreateATask() throws Exception {
        var userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userDetails.getUser();

        Task task = new Task();
        task.setDescription("Test 1");
        task.setCreatedBy(user);
        task.setAssignedTo(Collections.singletonList(user));

        var createTaskDto = new CreateTaskDto();
        createTaskDto.setDescription(task.getDescription());
        createTaskDto.setAssignedTo(Collections.singletonList(user.getId()));

        when(taskService.createTask(any(AppUser.class), any(Task.class))).thenReturn(task);

        this.mockMvc.perform(
                post("/api/tasks/create")
                        .content(objectMapper.writeValueAsString(createTaskDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.createdBy.username").value(user.getUsername()))
                .andExpect(jsonPath("$.description").value(task.getDescription()));

        verify(taskService).createTask(eq(user), any(Task.class));
    }

    @Test
    @WithMockAppUser
    public void shouldReturnTaskByIdForGivenUser() throws Exception {
        var userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userDetails.getUser();

        Task task = new Task();
        task.setCreatedBy(user);
        task.setAssignedTo(Collections.singletonList(user));
        task.setDescription("Test 2");

        when(taskService.getById(any(AppUser.class), any(UUID.class))).thenReturn(Optional.of(task));

        this.mockMvc.perform(get("/api/tasks/" + task.getId().toString()))
                .andDo(print())
                .andExpect(status()
                        .isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.createdBy.username").value(user.getUsername()))
                .andExpect(jsonPath("$.assignedTo[0].username").value(user.getUsername()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.id").value(task.getId().toString()));

        verify(taskService).getById(user, task.getId());
    }

    @Test
    @WithMockAppUser
    public void shouldUpdateTaskByTaskId() throws Exception {
        var userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userDetails.getUser();

        var task = new Task();
        task.setCreatedBy(user);
        task.setDescription("Test");
        task.setAssignedTo(Collections.singletonList(user));

        var putTaskDto = new CreateTaskDto();
        putTaskDto.setDescription(task.getDescription());
        putTaskDto.setAssignedTo(task.getAssignedTo().stream().map(AppUser::getId).collect(Collectors.toList()));

        when(taskService.updateById(any(AppUser.class), any(UUID.class), any(Task.class))).thenReturn(Optional.of(task));

        this.mockMvc.perform(put("/api/tasks/" + task.getId().toString())
                .content(objectMapper.writeValueAsString(putTaskDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status()
                        .isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.createdBy.username").value(user.getUsername()))
                .andExpect(jsonPath("$.assignedTo[0].username").value(user.getUsername()))
                .andExpect(jsonPath("$.description").value(task.getDescription()))
                .andExpect(jsonPath("$.id").value(task.getId().toString()));;

                verify(taskService).updateById(eq(user), eq(task.getId()), any(Task.class));
    }

    @Test
    @WithMockAppUser
    public void shouldFinishATask() throws Exception {
        var userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userDetails.getUser();

        var task = new Task();
        task.setCreatedBy(user);
        task.setDescription("Test");
        task.setState(TaskState.FINISHED);
        task.setAssignedTo(Collections.singletonList(user));

        when(taskService.finishById(any(AppUser.class), any(UUID.class))).thenReturn(Optional.of(task));

        this.mockMvc.perform(patch("/api/tasks/" + task.getId().toString() + "/finish"))
                .andDo(print())
                .andExpect(status()
                        .isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(task.getId().toString()))
                .andExpect(jsonPath("$.state").value(TaskState.FINISHED.toString()));

        verify(taskService).finishById(user, task.getId());
    }

    @Test
    @WithMockAppUser
    public void shouldReturnForbiddenFinishById() throws Exception {
        var task = new Task();
        task.setDescription("Test");
        when(taskService.finishById(any(AppUser.class), any(UUID.class)))
                .thenThrow(new TaskNotCreatedByAndNotAssignedToForbiddenException(task.getId()));

        this.mockMvc.perform(patch("/api/tasks/" + task.getId() + "/finish"))
                .andDo(print())
                .andExpect(status()
                        .isForbidden());
    }

    @Test
    @WithMockAppUser
    public void shouldReturnForbiddenUpdateTaskById() throws Exception {
        var task = new Task();
        task.setDescription("Test");
        when(taskService.updateById(any(AppUser.class), any(UUID.class), any(Task.class)))
                .thenThrow(new TaskNotCreatedByAndNotAssignedToForbiddenException(task.getId()));
        var createTaskDto = new CreateTaskDto();
        createTaskDto.setDescription(task.getDescription());

        this.mockMvc.perform(put("/api/tasks/" + task.getId())
                .content(objectMapper.writeValueAsString(createTaskDto)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status()
                        .isForbidden());
    }

    @Test
    @WithMockAppUser
    public void shouldReturnForbiddenGetTaskById() throws Exception {
        var task = new Task();
        when(taskService.getById(any(AppUser.class), eq(task.getId())))
                .thenThrow(new TaskNotCreatedByAndNotAssignedToForbiddenException(task.getId()));

        this.mockMvc.perform(get("/api/tasks/" + task.getId()))
                .andDo(print())
                .andExpect(status()
                        .isForbidden());
    }

    @Test
    @WithMockAppUser
    public void shouldReturnForbiddenDeleteATask() throws Exception {
        var task = new Task();
        when(taskService.deleteTaskById(any(AppUser.class), eq(task.getId())))
                .thenThrow(new TaskNotCreatedByAndNotAssignedToForbiddenException(task.getId()));

        this.mockMvc.perform(delete("/api/tasks/" + task.getId()))
                .andDo(print())
                .andExpect(status()
                        .isForbidden());
    }

    @Test
    public void shouldReturnUnauthorizedDeleteATaskWithoutCredentials() throws Exception {
        var task = new Task();
        this.mockMvc.perform(delete("/api/tasks/" + task.getId()))
                .andDo(print())
                .andExpect(status()
                        .isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorizedGettingTaskWithoutCredentials() throws Exception {
        this.mockMvc.perform(get("/api/tasks"))
                .andDo(print())
                .andExpect(status()
                        .isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorizedCreateTaskWithoutCredentials() throws Exception {
        var task = new Task();
        task.setDescription("Test");

        when(taskService.createTask(any(AppUser.class), any(Task.class))).thenReturn(task);

        this.mockMvc.perform(
                post("/api/tasks/create")
                        .content(objectMapper.writeValueAsString(task))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorizedUpdateTaskWithoutCredentials() throws Exception {
        var task = new Task();
        task.setDescription("Test");

        when(taskService.updateById(any(AppUser.class), any(UUID.class), any(Task.class))).thenReturn(Optional.of(task));

        this.mockMvc.perform(
                put("/api/tasks/create")
                        .content(objectMapper.writeValueAsString(task))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorizedFinishTaskWithoutCredentials() throws Exception {
        var task = new Task();
        task.setDescription("Test");

        when(taskService.finishById(any(AppUser.class), any(UUID.class))).thenReturn(Optional.of(task));

        this.mockMvc.perform(
                patch("/api/tasks/" + task.getId() + "/finish"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockAppUser
    public void shouldReturn404NotFoundGetTaskById() throws Exception {
        var userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userDetails.getUser();

        Task task = new Task();

        when(taskService.getById(any(AppUser.class), any(UUID.class))).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/api/tasks/" + task.getId().toString()))
                .andDo(print())
                .andExpect(status()
                        .isNotFound());

        verify(taskService).getById(user, task.getId());
    }

    @Test
    @WithMockAppUser
    public void shouldReturn404NotFoundUpdateTaskById() throws Exception {
        var userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userDetails.getUser();
        var task = new Task();
        task.setDescription("Test");
        CreateTaskDto createTaskDto = new CreateTaskDto();
        createTaskDto.setDescription(task.getDescription());
        createTaskDto.setState(TaskState.TODO);

        when(taskService.updateById(any(AppUser.class), any(UUID.class), any(Task.class))).thenReturn(Optional.empty());

        this.mockMvc.perform(put("/api/tasks/" + task.getId().toString())
                .content(objectMapper.writeValueAsString(createTaskDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status()
                        .isNotFound());

        verify(taskService).updateById(eq(user), eq(task.getId()), any(Task.class));
    }

    @Test
    @WithMockAppUser
    public void shouldReturn404NotFoundFinishTaskById() throws Exception {
        var userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userDetails.getUser();

        Task task = new Task();

        when(taskService.finishById(any(AppUser.class), any(UUID.class))).thenReturn(Optional.empty());

        this.mockMvc.perform(patch("/api/tasks/" + task.getId().toString() + "/finish"))
                .andDo(print())
                .andExpect(status()
                        .isNotFound());

        verify(taskService).finishById(eq(user), eq(task.getId()));
    }
}
