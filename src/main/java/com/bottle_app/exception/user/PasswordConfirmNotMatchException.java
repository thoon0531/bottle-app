package com.bottle_app.exception.user;

public class PasswordConfirmNotMatchException extends RuntimeException {

    public PasswordConfirmNotMatchException(String message) {
        super(message);
    }
}
