package com.rsginer.springboottodolist.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsginer.springboottodolist.auth.domain.AppUserDetails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ApiMonitor {
    private final static Log log = LogFactory.getLog(ApiMonitor.class);

    ObjectMapper objMapper = new ObjectMapper();

    @Pointcut("execution(public * com.rsginer.*..*(..))")
    private void anyPublicOperation() {
    }


    @Pointcut("within(com.rsginer.springboottodolist.*..api.**)")
    private void apiOperation() {
    }

    @After("anyPublicOperation() && apiOperation() && args(param)")
    public Object requestApi(JoinPoint joinPoint, String param) {
        System.out.println("WORKS!");
        var auth = SecurityContextHolder.getContext().getAuthentication();

        log.info("Method: " + joinPoint.getSignature().getName());
        log.info("Parameters:" + Arrays.toString(joinPoint.getArgs()));
        log.info("Param: " + param);

        if (auth.isAuthenticated()) {
            var userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            var user = userDetails.getUser();
            try {
                log.info("AppUser: " + objMapper.writeValueAsString(user.toDto()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return joinPoint;
    }

}
