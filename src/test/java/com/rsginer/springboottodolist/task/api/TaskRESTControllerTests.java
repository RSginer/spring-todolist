package com.rsginer.springboottodolist.task.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsginer.springboottodolist.security.MockSecurityRESTController;
import com.rsginer.springboottodolist.security.WithMockAppUser;
import com.rsginer.springboottodolist.auth.domain.AppUserDetails;
import com.rsginer.springboottodolist.task.domain.Task;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void shouldReturnUnauthorizedGettingTaskWithoutCredentials() throws Exception {
        this.mockMvc.perform(get("/api/tasks"))
                .andDo(print())
                .andExpect(status()
                        .isUnauthorized());
    }

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
        createTaskDto.setAsignedTo(Collections.singletonList(user.getId()));

        when(taskService.createTask(any(AppUser.class), any(Task.class))).thenReturn(task);

        this.mockMvc.perform(
                post("/api/tasks/create")
                        .content(objectMapper.writeValueAsString(createTaskDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.createdBy.username").value(user.getUsername()))
                .andExpect(jsonPath("$.description").value(task.getDescription()));
    }

}
