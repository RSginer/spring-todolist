package com.rsginer.springboottodolist.auth.service;

import com.rsginer.springboottodolist.auth.domain.AppUserDetails;
import com.rsginer.springboottodolist.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AppUserService appUserService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var user = appUserService.findByUsername(s);

        if (user == null) {
            throw new UsernameNotFoundException("User with username " + s + " not found");
        }

        return new AppUserDetails(user);
    }
}
