package com.bottle_app.service;

import com.bottle_app.dto.LoginRequestDto;
import com.bottle_app.dto.RegisterRequestDto;
import com.bottle_app.dto.TokenDto;
import com.bottle_app.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {
    //register
    User createUser(RegisterRequestDto registerRequestDto);

    //login
    TokenDto login(LoginRequestDto loginRequestDto);

    //select bottle receiver
    Optional<User> getRandomUserExcludeSelf(User user);

    //select bottle creator and update last bottle creation date
    User selectUser(Long id);

    //email verification
    public String generateVerfication(String username);
    public String getVerficationIdByUsername(String username);
    public String getUsernameForVerificationId(String verficationId);

    //CRUD
    User findByUsername(String username);
    User save(User user);
}
