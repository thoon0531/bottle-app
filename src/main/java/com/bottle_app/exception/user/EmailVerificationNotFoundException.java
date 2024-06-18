package com.bottle_app.exception.user;

public class EmailVerificationNotFoundException extends RuntimeException {

    public EmailVerificationNotFoundException(String message) {
        super(message);
    }
}
