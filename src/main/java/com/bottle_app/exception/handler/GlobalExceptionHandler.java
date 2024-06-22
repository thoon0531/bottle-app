package com.bottle_app.exception.handler;

import com.bottle_app.dto.DefaultResponseDto;
import com.bottle_app.exception.bottle.BottleNotFoundException;
import com.bottle_app.exception.user.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(value = {BottleNotFoundException.class})
    public ResponseEntity<DefaultResponseDto> handlerBottleNotFoundException(BottleNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DefaultResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    public ResponseEntity<DefaultResponseDto> handlerUserAlreadyExistsException(UserAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new DefaultResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(value = {PasswordConfirmNotMatchException.class})
    public ResponseEntity<DefaultResponseDto> handlerPasswordConfirmNotMatchException(PasswordConfirmNotMatchException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DefaultResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<DefaultResponseDto> handlerUserNotFoundException(UserNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DefaultResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(value = {UserIsNotVerifiedException.class})
    public ResponseEntity<DefaultResponseDto> handlerUserIsNotVerified(UserIsNotVerifiedException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new DefaultResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(value = {EmailVerificationNotFoundException.class})
    public ResponseEntity<DefaultResponseDto> handlerEmailVerificationNotFoundException(EmailVerificationNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DefaultResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<DefaultResponseDto> handlerIllegalArgumentException(IllegalArgumentException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DefaultResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(value = {TokenInvaildException.class})
    public ResponseEntity<DefaultResponseDto> handlerTokenInvaildException(TokenInvaildException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DefaultResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(value = {RefreshTokenNotMatchException.class})
    public ResponseEntity<DefaultResponseDto> handlerRefreshTokenNotMatchException(RefreshTokenNotMatchException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new DefaultResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<DefaultResponseDto> handlerConstraintViolationException(ConstraintViolationException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DefaultResponseDto(ex.getMessage()));
    }
}

