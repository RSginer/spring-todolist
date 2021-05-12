package com.rsginer.springboottodolist.auth.service;

import com.rsginer.springboottodolist.auth.domain.AppUserDetails;
import com.rsginer.springboottodolist.user.domain.AppUser;
import com.rsginer.springboottodolist.user.service.AppUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserDetailsServiceImplTests {
    @Mock
    AppUserService appUserService;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void shouldReturnUserDetailsByUsername() {
        var user = new AppUser();
        user.setUsername("test@test.com");
        user.setPassword("Test");
        user.setFirstName("Test");
        user.setLastName("Test");

        var userDetailsExpected = new AppUserDetails(user);

        when(appUserService.findByUsername(any(String.class))).thenReturn(user);

        var userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        assertThat(userDetails.getUsername()).isSameAs(userDetailsExpected.getUsername());
        assertThat(userDetails.getAuthorities()).isEqualTo(userDetailsExpected.getAuthorities());
        assertThat(userDetails.getPassword()).isSameAs(userDetailsExpected.getPassword());
        assertThat(userDetails.isAccountNonExpired()).isSameAs(userDetailsExpected.isAccountNonExpired());
        assertThat(userDetails.isAccountNonLocked()).isSameAs(userDetailsExpected.isAccountNonLocked());
        assertThat(userDetails.isEnabled()).isSameAs(userDetailsExpected.isEnabled());
        assertThat(userDetails.isCredentialsNonExpired()).isSameAs(userDetailsExpected.isCredentialsNonExpired());

        verify(appUserService).findByUsername(user.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrowUsernameNotFoundException() {
        var user = new AppUser();
        user.setUsername("test@test.com");
        user.setPassword("Test");
        user.setFirstName("Test");
        user.setLastName("Test");

        when(appUserService.findByUsername(any(String.class))).thenReturn(null);

        var userDetails = userDetailsService.loadUserByUsername(user.getUsername());
    }
}
