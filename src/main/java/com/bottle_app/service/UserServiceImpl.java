package com.bottle_app.service;

import com.bottle_app.dto.LoginRequestDto;
import com.bottle_app.dto.RegisterRequestDto;
import com.bottle_app.dto.TokenDto;
import com.bottle_app.exception.user.*;
import com.bottle_app.model.EmailVerification;
import com.bottle_app.model.Role;
import com.bottle_app.model.User;
import com.bottle_app.repository.EmailVerificationRepository;
import com.bottle_app.repository.UserRepository;
import com.bottle_app.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public User createUser(RegisterRequestDto registerRequestDto) {
        Date now = new Date();
        User user = User.builder()
                .username(registerRequestDto.getName())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .email(registerRequestDto.getEmail())
                .createdAt(now)
                .updatedAt(now)
                .role(Role.USER)
                .verified(false)
                .randId((long)(Math.random()*1000000))
                .lastBottleCreation(null)
                .build();
        //TO DO
        //check id already exists
        if(userRepository.findByUsername(registerRequestDto.getName()).isPresent()){
            throw new UserAlreadyExistsException("Username already exists");
        }
        //check email exists
        if(userRepository.findByEmail(registerRequestDto.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("Email already exists");
        }
        //check password, email validation
        //check password matches
        if(!registerRequestDto.getPassword().matches(registerRequestDto.getPasswordConfirm())){
            throw new PasswordConfirmNotMatchException("PasswordConfirm is not match");
        }
        return userRepository.save(user);

    }

    @Override
    @Transactional
    public TokenDto login(LoginRequestDto loginRequestDto) {
        //check ID
        User user = userRepository.findByUsername(loginRequestDto.getName()).orElseThrow(
                () -> new UserNotFoundException(String.format("Username %s is not exists", loginRequestDto.getName()))
        );
        //check password,email validation
        //check password
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new PasswordConfirmNotMatchException("Wrong password");
        }
        //check verified
        if(!user.getVerified()){
            throw new UserIsNotVerifiedException("You are not verified. Please verify your email address.");
        }

        //create jwt token
        return jwtUtil.generateToken(user);

    }

    @Override
    @Transactional
    public Optional<User> getRandomUserExcludeSelf(User user) {
        long count = userRepository.countByIdNot(user.getId());
        Optional<User> userOptional = Optional.empty();
        //log.info("Get random user");
        //log.info("Get user count excluding self: {}", count);

        if(count > 0){
            while(userOptional.isEmpty()){
                userOptional = userRepository.findRandByIdNot(user.getId());
            }
            //log.info("Get random user excluding self: {}", userOptional);
        }

        return userOptional;
    }

    @Override
    @Transactional
    //select bottle creator and update last_bottle_creation
    public User selectUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        user.setLastBottleCreation(new Date());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public String generateVerfication(String username) {
        if(!emailVerificationRepository.existsByUsername(username)){
            EmailVerification emailVerification = new EmailVerification(username);
            emailVerification = emailVerificationRepository.save(emailVerification);
            return emailVerification.getVerificationId().toString();
        }
        return getVerficationIdByUsername(username);
    }

    @Override
    @Transactional
    public String getVerficationIdByUsername(String username) {
        EmailVerification emailVerification =
                emailVerificationRepository.findByUsername(username);
        if(emailVerification != null){
            return emailVerification.getVerificationId().toString();
        }
        throw new EmailVerificationNotFoundException("Email verification not found");
    }

    @Override
    @Transactional
    public String getUsernameForVerificationId(String verficationId) {
        EmailVerification emailVerification = emailVerificationRepository.findById(UUID.fromString(verficationId)).orElseThrow(
                () -> new EmailVerificationNotFoundException("Email verification not found")
        );
        return emailVerification.getUsername();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }


}
