package com.rsginer.springboottodolist.user.service;

import com.rsginer.springboottodolist.user.domain.AppUser;
import com.rsginer.springboottodolist.user.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService{
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUser signUp(AppUser appUser) throws AppUserExistsException {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword().trim()));

        var existingUser = appUserRepository.findByUsername(appUser.getUsername());

        if (existingUser != null) {
            throw new AppUserExistsException(existingUser.getUsername());
        }

        appUserRepository.save(appUser);

        return appUser;
    }

    @Override
    public AppUser findByUsername(String username) {
        return this.appUserRepository.findByUsername(username);
    }
}
