package com.bottle_app.service;

import com.bottle_app.dto.LoginRequestDto;
import com.bottle_app.dto.RegisterRequestDto;
import com.bottle_app.dto.TokenDto;
import com.bottle_app.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {
    void createUser(RegisterRequestDto registerRequestDto);

    TokenDto login(LoginRequestDto loginRequestDto);

    Optional<User> getRandomUserExcludeSelf(User user);

    User selectUser(Long id);
}
