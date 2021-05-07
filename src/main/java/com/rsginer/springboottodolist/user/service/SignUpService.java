package com.rsginer.springboottodolist.user.service;

import com.rsginer.springboottodolist.user.AppUser;
import com.rsginer.springboottodolist.user.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean signUp(AppUser appUser) throws AppUserExistsException {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        var existingUser = appUserRepository.findByUsername(appUser.getUsername());

        if (existingUser != null) {
            throw new AppUserExistsException();
        }

        appUserRepository.save(appUser);

        return true;
    }

}
