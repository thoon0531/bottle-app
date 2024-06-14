package com.bottle_app.controller;

import com.bottle_app.dto.LoginRequestDto;
import com.bottle_app.dto.RegisterRequestDto;
import com.bottle_app.dto.TokenDto;
import com.bottle_app.service.UserService;
import com.bottle_app.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    //register
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto registerRequestDto) {
        userService.createUser(registerRequestDto);

        return ResponseEntity.ok("User registered successfully");
    }

    //login
    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto);
    }
}
