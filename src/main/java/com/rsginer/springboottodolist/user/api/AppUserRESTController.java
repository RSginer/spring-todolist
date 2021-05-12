package com.rsginer.springboottodolist.user.api;

import com.rsginer.springboottodolist.user.mapper.AppUserMapper;
import com.rsginer.springboottodolist.user.dto.AppUserDto;
import com.rsginer.springboottodolist.user.service.AppUserExistsException;
import com.rsginer.springboottodolist.user.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "User")
@RestController
@RequestMapping("/api/user")
public class AppUserRESTController {

    @Autowired
    private AppUserService appUserService;


    @Operation(description = "Register new user")
    @PostMapping("/signUp")
    public AppUserDto signUp(@RequestBody(required = true) @Valid AppUserDto appUser) throws AppUserExistsException {
        return appUserService.signUp(new AppUserMapper().toEntity(appUser)).toDto();
    }
}
