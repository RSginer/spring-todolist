package com.rsginer.springboottodolist.config;

import com.rsginer.springboottodolist.task.service.TaskService;
import com.rsginer.springboottodolist.task.service.TaskServiceImpl;
import com.rsginer.springboottodolist.user.service.AppUserService;
import com.rsginer.springboottodolist.user.service.AppUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public TaskService taskService() {
        return new TaskServiceImpl();
    }

    @Bean
    public AppUserService appUserService() {
        return new AppUserServiceImpl();
    }
}
