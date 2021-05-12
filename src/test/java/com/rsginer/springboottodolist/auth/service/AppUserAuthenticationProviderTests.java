package com.rsginer.springboottodolist.auth.service;

import com.rsginer.springboottodolist.auth.domain.AppUserDetails;
import com.rsginer.springboottodolist.user.domain.AppUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AppUserAuthenticationProviderTests {
    @Mock
    UserDetailsService userDetailsService;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    AppUserAuthenticationProvider appUserAuthenticationProvider;

    @Test
    public void shouldReturnTrueSupport() {
        var support = appUserAuthenticationProvider.supports(UsernamePasswordAuthenticationToken.class);

        assertThat(support).isTrue();
    }

    @Test(expected = AuthenticationException.class)
    public void shouldThrowAuthenticationException() {
        when(userDetailsService.loadUserByUsername(any(String.class))).thenThrow(new UsernameNotFoundException(""));
        appUserAuthenticationProvider.authenticate(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return "Test";
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "test@test.com";
            }
        });
    }

    @Test
    public void shouldAuthenticateUser() {
        var user = new AppUser();
        user.setUsername("test@test.com");
        user.setPassword("Test");
        user.setFirstName("Test");
        user.setLastName("Test");
        var userDetails = new AppUserDetails(user);
        when(passwordEncoder.matches(any(CharSequence.class), any(String.class))).thenReturn(true);
        when(userDetailsService.loadUserByUsername(any(String.class))).thenReturn(userDetails);

        var authToken = appUserAuthenticationProvider.authenticate(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return user.getPassword();
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return user.getUsername();
            }
        });

        assertThat(authToken.isAuthenticated()).isTrue();
        assertThat(authToken.getPrincipal()).isInstanceOf(AppUserDetails.class);

        verify(passwordEncoder).matches("Test", "Test");
        verify(userDetailsService).loadUserByUsername("test@test.com");
    }
}
