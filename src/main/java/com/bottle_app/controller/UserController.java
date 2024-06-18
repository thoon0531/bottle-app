package com.bottle_app.controller;

import com.bottle_app.dto.DefaultResponseDto;
import com.bottle_app.dto.LoginRequestDto;
import com.bottle_app.dto.RegisterRequestDto;
import com.bottle_app.dto.TokenDto;
import com.bottle_app.event.UserRegistrationEvent;
import com.bottle_app.model.User;
import com.bottle_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    //register
    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public DefaultResponseDto register(@RequestBody RegisterRequestDto registerRequestDto) {
        User user = userService.createUser(registerRequestDto);
        eventPublisher.publishEvent(new UserRegistrationEvent(user));

        return new DefaultResponseDto("User registered successfully");
    }

    //login
    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.CREATED)
    public TokenDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto);
    }

    //email verifying
    @GetMapping("/verify/email")
    @ResponseStatus(code = HttpStatus.OK)
    public DefaultResponseDto verifyEmail(@RequestParam String id) {
        byte[] actualId = Base64.getDecoder().decode(id.getBytes());
        String username = userService.getUsernameForVerificationId(new String(actualId));

        User user = userService.findByUsername(username);
        user.setVerified(true);
        userService.save(user);
        return new DefaultResponseDto("User verified successfully");
    }
}
