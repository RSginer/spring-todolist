package com.rsginer.springboottodolist.user.service;

import com.rsginer.springboottodolist.user.AppUser;

import java.util.Optional;

public interface AppUserService {
    boolean signUp(AppUser appUser) throws AppUserExistsException;
    AppUser findByUsername(String username);
}
