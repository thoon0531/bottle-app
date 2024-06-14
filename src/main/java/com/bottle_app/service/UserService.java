package com.bottle_app.service;

import com.bottle_app.dto.LoginRequestDto;
import com.bottle_app.dto.RegisterRequestDto;
import com.bottle_app.dto.TokenDto;
import com.bottle_app.model.User;

import java.util.Optional;

public interface UserService {
    void createUser(RegisterRequestDto registerRequestDto);

    TokenDto login(LoginRequestDto loginRequestDto);
}
