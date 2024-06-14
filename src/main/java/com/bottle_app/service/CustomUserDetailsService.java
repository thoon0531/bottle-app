package com.bottle_app.service;

import com.bottle_app.model.User;
import com.bottle_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findByEmail(username);
        if(optional.isEmpty()) {
            throw new UsernameNotFoundException(username);
        } else{
            User user = optional.get();
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .disabled(!user.getVerified())
                    .accountExpired(user.getAccountCredentialsExpired())
                    .accountLocked(user.getLocked())
                    .roles(user.getRole().name())
                    .build();
        }
    }
}
