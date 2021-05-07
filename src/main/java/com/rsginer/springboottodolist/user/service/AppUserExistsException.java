package com.rsginer.springboottodolist.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AppUserExistsException extends Exception {
    public AppUserExistsException() {
        super("User already exist");
    }
}
