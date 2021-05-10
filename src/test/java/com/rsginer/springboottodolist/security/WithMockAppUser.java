package com.rsginer.springboottodolist.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockAppUser{

    String username() default "test@test.com";

    String password() default "test";

    String firstName() default "Test";

    String lastName() default "Test";
}
