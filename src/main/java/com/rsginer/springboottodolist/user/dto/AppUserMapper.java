package com.rsginer.springboottodolist.user.dto;

import com.rsginer.springboottodolist.user.AppUser;

public class AppUserMapper {
    public AppUser toEntity(AppUserDto appUserDto) {
        var user = new AppUser();
        user.setFirstName(appUserDto.getFirstName());
        user.setLastName(appUserDto.getLastName());
        user.setPassword(appUserDto.getPassword());
        user.setUsername(appUserDto.getUsername());
        return user;
    }
}
