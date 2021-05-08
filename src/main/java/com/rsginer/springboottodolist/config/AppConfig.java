package com.rsginer.springboottodolist.config;

import com.rsginer.springboottodolist.task.service.TaskService;
import com.rsginer.springboottodolist.task.service.TaskServiceImpl;
import com.rsginer.springboottodolist.user.service.AppUserService;
import com.rsginer.springboottodolist.user.service.AppUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class AppConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public TaskService taskService() {
        return new TaskServiceImpl();
    }

    @Bean
    public AppUserService appUserService() {
        return new AppUserServiceImpl();
    }
}
