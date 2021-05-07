package com.rsginer.springboottodolist.user.api;

import com.rsginer.springboottodolist.user.AppUser;
import com.rsginer.springboottodolist.user.dto.AppUserDto;
import com.rsginer.springboottodolist.user.service.AppUserExistsException;
import com.rsginer.springboottodolist.user.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class AppUserRESTController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping("/signUp")
    public AppUser signUp(@RequestBody AppUserDto appUserDto) throws AppUserExistsException {
        var user = new AppUser();
        user.setFirstName(appUserDto.getFirstName());
        user.setLastName(appUserDto.getLastName());
        user.setPassword(appUserDto.getPassword());
        user.setUsername(appUserDto.getUsername());

        return signUpService.signUp(user);
    }
}
