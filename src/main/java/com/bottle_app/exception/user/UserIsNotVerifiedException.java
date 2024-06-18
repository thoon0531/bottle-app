package com.bottle_app.exception.user;

public class UserIsNotVerifiedException extends RuntimeException {

    public UserIsNotVerifiedException(String message) {
        super(message);
    }
}
