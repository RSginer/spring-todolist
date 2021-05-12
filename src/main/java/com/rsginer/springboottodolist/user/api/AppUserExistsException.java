package com.rsginer.springboottodolist.user.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AppUserExistsException extends Exception {
    public AppUserExistsException(String username) {
        super("User " + username + " already exist");
    }
}
