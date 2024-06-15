package com.bottle_app.service;

import com.bottle_app.dto.LoginRequestDto;
import com.bottle_app.dto.RegisterRequestDto;
import com.bottle_app.dto.TokenDto;
import com.bottle_app.model.Role;
import com.bottle_app.model.User;
import com.bottle_app.repository.UserRepository;
import com.bottle_app.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public void createUser(RegisterRequestDto registerRequestDto) {
        Date now = new Date();
        User user = User.builder()
                .username(registerRequestDto.getName())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .email(registerRequestDto.getEmail())
                .createdAt(now)
                .updatedAt(now)
                .role(Role.USER)
                .verified(true)
                .randId((long)(Math.random()*1000000))
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
    @Transactional
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

    @Override
    @Transactional
    public Optional<User> getRandomUserExcludeSelf(User user) {
        long count = userRepository.countByIdNot(user.getId());
        Optional<User> userOptional = Optional.empty();
        log.info("Get random user");
        log.info("Get user count excluding self: {}", count);

        if(count > 0){
            userOptional = userRepository.findRandByIdNot((long)(Math.random()*1000000));
        }

        return userOptional;
    }

    @Override
    public User selectUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }


}
