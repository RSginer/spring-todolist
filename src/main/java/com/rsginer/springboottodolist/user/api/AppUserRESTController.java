package com.rsginer.springboottodolist.user.api;

import com.rsginer.springboottodolist.user.dto.AppUserMapper;
import com.rsginer.springboottodolist.user.dto.AppUserDto;
import com.rsginer.springboottodolist.user.service.AppUserExistsException;
import com.rsginer.springboottodolist.user.service.AppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "User", tags = "User")
@RestController
@RequestMapping("/api/user")
public class AppUserRESTController {

    @Autowired
    private AppUserService appUserService;


    @ApiOperation(value = "Register new user")
    @PostMapping("/signUp")
    public boolean signUp(@RequestBody AppUserDto appUserDto) throws AppUserExistsException {
        return appUserService.signUp(new AppUserMapper().toEntity(appUserDto));
    }
}
