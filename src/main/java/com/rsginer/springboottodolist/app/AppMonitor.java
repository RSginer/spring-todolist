package com.rsginer.springboottodolist.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsginer.springboottodolist.auth.domain.AppUserDetails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AppMonitor {
    ObjectMapper objMapper = new ObjectMapper();

    private final static Log log = LogFactory.getLog(AppMonitor.class);

    @AfterReturning("execution(public * *(..)) && bean(*Controller)")
    public void logRestApiMethods(JoinPoint joinPoint) {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        log.info("Method: " + joinPoint.getSignature().getName());

        try {
            log.info("Parameters:" + objMapper.writeValueAsString(joinPoint.getArgs()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (auth.getPrincipal() != null && auth.getPrincipal() != "anonymousUser") {
            var userDetails = (AppUserDetails) auth.getPrincipal();
            var user = userDetails.getUser();
            try {
                log.info("AppUser: " + objMapper.writeValueAsString(user.toDto()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            log.info("AppUser: Anonymous");
        }
    }
}
