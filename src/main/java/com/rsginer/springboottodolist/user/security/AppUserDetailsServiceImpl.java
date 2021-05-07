package com.rsginer.springboottodolist.user.security;

import com.rsginer.springboottodolist.user.AppUser;
import com.rsginer.springboottodolist.user.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("We can not find this user: " + username);
        }

        return new AppUserDetails(user);
    }
}
