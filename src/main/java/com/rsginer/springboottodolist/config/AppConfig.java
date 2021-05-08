package com.rsginer.springboottodolist.config;

import com.rsginer.springboottodolist.task.service.TaskService;
import com.rsginer.springboottodolist.task.service.TaskServiceImpl;
import com.rsginer.springboottodolist.user.service.AppUserService;
import com.rsginer.springboottodolist.user.service.AppUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class AppConfig {
    @Bean
    public Docket api() {
        ArrayList<SecurityScheme> auth = new ArrayList<>(1);
        auth.add(new BasicAuth("basicAuth"));

        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(auth)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
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
