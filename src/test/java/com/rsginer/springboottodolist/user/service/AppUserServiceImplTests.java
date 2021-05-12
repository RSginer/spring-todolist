package com.rsginer.springboottodolist.user.service;

import com.rsginer.springboottodolist.user.api.AppUserExistsException;
import com.rsginer.springboottodolist.user.domain.AppUser;
import com.rsginer.springboottodolist.user.repository.AppUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AppUserServiceImplTests {

    @Mock
    AppUserRepository appUserRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    AppUserServiceImpl appUserService;

    @Test
    public void shouldCreateNewUserAndEncodePasswordIfNotExist() throws AppUserExistsException {
        var user = new AppUser();
        user.setUsername("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Test");
        var unencryptedPassword = "Test";
        user.setPassword(unencryptedPassword);
        var encryptedPassword = "ENCRYPTED";
        when(passwordEncoder.encode(any(String.class))).thenReturn(encryptedPassword);
        when(appUserRepository.findByUsername(any(String.class))).thenReturn(null);
        when(appUserRepository.save(any(AppUser.class))).thenReturn(user);

        var registeredUser = appUserService.signUp(user);

        assertThat(registeredUser.getId()).isSameAs(user.getId());
        assertThat(registeredUser.getPassword()).isSameAs(encryptedPassword);

        verify(appUserRepository).save(registeredUser);
        verify(appUserRepository).findByUsername(user.getUsername());
        verify(passwordEncoder).encode(unencryptedPassword);
    }

    @Test(expected = AppUserExistsException.class)
    public void shouldThrowAppUserExistsExceptionWhenCreateExistingUser() throws AppUserExistsException {
        var user = new AppUser();
        user.setUsername("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Test");
        var unencryptedPassword = "Test";
        user.setPassword(unencryptedPassword);
        var encryptedPassword = "ENCRYPTED";
        when(passwordEncoder.encode(any(String.class))).thenReturn(encryptedPassword);
        when(appUserRepository.findByUsername(any(String.class))).thenReturn(user);

        appUserService.signUp(user);

        verify(appUserRepository).findByUsername(user.getUsername());
        verify(passwordEncoder).encode(unencryptedPassword);
    }

    @Test
    public void shouldReturnUserWhenFindExistingUsername() {
        var user = new AppUser();
        user.setUsername("test@test.com");
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword("Test");

        when(appUserRepository.findByUsername(any(String.class))).thenReturn(user);

        var foundUser = appUserService.findByUsername(user.getUsername());

        assertThat(foundUser.getId()).isSameAs(user.getId());
        verify(appUserRepository).findByUsername(user.getUsername());
    }

    @Test
    public void shouldReturnNullWhenFindNotExistingUsername() {
        var username = "Test";

        when(appUserRepository.findByUsername(any(String.class))).thenReturn(null);

        var foundUser = appUserService.findByUsername(username);

        assertThat(foundUser).isSameAs(null);
        verify(appUserRepository).findByUsername(username);
    }
}
