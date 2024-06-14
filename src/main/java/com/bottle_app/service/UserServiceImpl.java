package com.bottle_app.service;

import com.bottle_app.dto.LoginRequestDto;
import com.bottle_app.dto.RegisterRequestDto;
import com.bottle_app.dto.TokenDto;
import com.bottle_app.model.Role;
import com.bottle_app.model.User;
import com.bottle_app.repository.UserRepository;
import com.bottle_app.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void createUser(RegisterRequestDto registerRequestDto) {
        Date now = new Date();
        User user = User.builder()
                .username(registerRequestDto.getName())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .email(registerRequestDto.getEmail())
                .createdAt(now)
                .updatedAt(now)
                .role(Role.ROLE_USER)
                .verified(false)
                .build();
        //TO DO
        //비밀번호, 이메일 유효성 검사, 비밀번호 확인
        if(userRepository.findByUsername(registerRequestDto.getName()).isPresent()){
            throw new RuntimeException("Username already exists");
        }
        if(userRepository.findByEmail(registerRequestDto.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists");
        }
        if(!registerRequestDto.getPassword().matches(registerRequestDto.getPasswordConfirm())){
            throw new RuntimeException("Passwords do not match");
        }
        userRepository.save(user);

    }

    @Override
    public TokenDto login(LoginRequestDto loginRequestDto) {
        //check ID
        User user = userRepository.findByUsername(loginRequestDto.getName()).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        //check password
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        //create jwt token
        return jwtUtil.generateToken(user);

    }


}
