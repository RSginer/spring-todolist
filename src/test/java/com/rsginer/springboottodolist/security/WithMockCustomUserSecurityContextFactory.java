package com.rsginer.springboottodolist.security;

import com.rsginer.springboottodolist.security.auth.AppUserDetails;
import com.rsginer.springboottodolist.user.AppUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory
        implements WithSecurityContextFactory<WithMockAppUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockAppUser mockAppUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        var user = new AppUser();
        user.setUsername(mockAppUser.username());
        user.setPassword(mockAppUser.password());
        user.setFirstName(mockAppUser.firstName());
        user.setLastName(mockAppUser.lastName());

        AppUserDetails principal =
                new AppUserDetails(user);
        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}
