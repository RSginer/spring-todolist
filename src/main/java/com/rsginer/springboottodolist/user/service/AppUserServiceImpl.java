package com.rsginer.springboottodolist.user.service;

import com.rsginer.springboottodolist.user.domain.AppUser;
import com.rsginer.springboottodolist.user.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService{
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserServiceImpl(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
