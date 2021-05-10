package com.rsginer.springboottodolist.task.api;

import com.rsginer.springboottodolist.task.service.TaskService;
import com.rsginer.springboottodolist.user.dto.AppUserDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskRESTControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private String credentials;

    @Test
    public void shouldReturnForbiddenGettingTaskWithoutCredentials() throws Exception {
        this.mockMvc.perform(get("/api/task"))
                .andDo(print())
                .andExpect(status()
                        .isUnauthorized());
    }

    @WithMockUser
    @WithUserDetails
    @Test
    public void shouldReturnTaskForTestUser() throws Exception {
        this.mockMvc.perform(get("/api/tasks"))
                .andDo(print())
                .andExpect(status()
                        .isOk());
    }

}
