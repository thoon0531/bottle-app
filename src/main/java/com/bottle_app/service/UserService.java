package com.bottle_app.service;

import com.bottle_app.model.User;

import java.util.Optional;

public interface UserService {
    User createUser(User user);

    Optional<User> getUserById(long userid);


    void updateUser(long userid, User bottle);

    void deleteUserById(long userid);

}
