package com.bottle_app.controller;

import com.bottle_app.dto.DefaultResponseDto;
import com.bottle_app.dto.LoginRequestDto;
import com.bottle_app.dto.RegisterRequestDto;
import com.bottle_app.dto.TokenDto;
import com.bottle_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //register
    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public DefaultResponseDto register(@RequestBody RegisterRequestDto registerRequestDto) {
        userService.createUser(registerRequestDto);

        return new DefaultResponseDto("User registered successfully");
    }

    //login
    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.CREATED)
    public TokenDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto);
    }
}
