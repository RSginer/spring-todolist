package com.rsginer.springboottodolist.security;

import com.rsginer.springboottodolist.security.auth.AppUserAuthenticationProvider;
import com.rsginer.springboottodolist.security.auth.UserDetailsServiceImpl;
import com.rsginer.springboottodolist.user.service.AppUserService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

public class MockSecurityRESTController {
    @MockBean(value = UserDetailsServiceImpl.class)
    private UserDetailsService userDetailsService;

    @MockBean(value = AppUserAuthenticationProvider.class)
    private AuthenticationProvider authenticationProvider;

    @MockBean
    private AppUserService appUserService;
}
