package com.rsginer.springboottodolist;

import static org.assertj.core.api.Assertions.assertThat;

import com.rsginer.springboottodolist.task.api.TaskRESTController;
import com.rsginer.springboottodolist.task.service.TaskService;
import com.rsginer.springboottodolist.user.service.AppUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootTodoListApplicationTests {

    @Autowired
    private TaskService taskService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private TaskRESTController taskRESTController;

    @Test
    void contextLoads() {
        assertThat(taskService).isNotNull();
        assertThat(appUserService).isNotNull();
        assertThat(taskRESTController).isNotNull();
    }

}
