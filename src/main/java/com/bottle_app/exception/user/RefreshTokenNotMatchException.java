package com.bottle_app.exception.user;

public class RefreshTokenNotMatchException extends RuntimeException {

    public RefreshTokenNotMatchException(String message) {
        super(message);
    }
}
