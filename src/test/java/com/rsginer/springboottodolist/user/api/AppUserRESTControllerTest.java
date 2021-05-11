package com.rsginer.springboottodolist.user.api;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(AppUserRESTController.class)
@EnableSpringDataWebSupport
public class AppUserRESTControllerTest {
    @Autowired
    private MockMvc mockMvc;

}
