package com.rsginer.springboottodolist.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsginer.springboottodolist.user.domain.AppUser;
import com.rsginer.springboottodolist.user.service.AppUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AppUserRESTController.class)
@EnableSpringDataWebSupport
public class AppUserRESTControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private AppUserService appUserService;

    @Test
    public void shouldCreateNewUser() throws Exception {
        var user = new AppUser();
        user.setUsername("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword("Test");

        when(appUserService.signUp(any(AppUser.class))).thenReturn(user);

        this.mockMvc.perform(
                post("/api/user/signUp")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status()
                        .isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(user.getUsername()));
    }


}
