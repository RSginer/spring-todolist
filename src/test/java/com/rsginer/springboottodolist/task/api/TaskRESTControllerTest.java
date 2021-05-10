package com.rsginer.springboottodolist.task.api;

import com.rsginer.springboottodolist.security.WithMockAppUser;
import com.rsginer.springboottodolist.security.auth.UserDetailsServiceImpl;
import com.rsginer.springboottodolist.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskRESTControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnForbiddenGettingTaskWithoutCredentials() throws Exception {
        this.mockMvc.perform(get("/api/task"))
                .andDo(print())
                .andExpect(status()
                        .isUnauthorized());
    }

    @WithMockAppUser
    @Test
    public void shouldReturnTaskForTestUser() throws Exception {
        this.mockMvc.perform(get("/api/tasks"))
                .andDo(print())
                .andExpect(status()
                        .isOk());
    }

}
