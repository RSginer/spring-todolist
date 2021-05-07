package com.rsginer.springboottodolist.user.service;

public class AppUserExistsException extends Exception {
    public AppUserExistsException() {
        super("User already exist");
    }
}
