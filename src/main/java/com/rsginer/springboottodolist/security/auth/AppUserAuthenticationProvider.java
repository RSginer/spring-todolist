package com.rsginer.springboottodolist.security.auth;

import com.rsginer.springboottodolist.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AppUserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var username = authentication.getName();
        var rawPassword = authentication.getCredentials().toString().trim();
        var user = appUserService.findByUsername(username);


        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    user,
                    user.getPassword(),
                    AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_AUTHENTICATED")
            );
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
