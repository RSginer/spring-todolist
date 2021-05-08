package com.rsginer.springboottodolist.user.service;

import com.rsginer.springboottodolist.user.AppUser;

public interface AppUserService {
    AppUser signUp(AppUser appUser) throws AppUserExistsException;
    AppUser findByUsername(String username);
}
