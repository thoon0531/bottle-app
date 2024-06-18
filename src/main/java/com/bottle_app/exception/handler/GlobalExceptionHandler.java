package com.bottle_app.exception.handler;

import com.bottle_app.dto.DefaultResponseDto;
import com.bottle_app.exception.bottle.BottleNotFoundException;
import com.bottle_app.exception.user.PasswordConfirmNotMatchException;
import com.bottle_app.exception.user.UserAlreadyExistsException;
import com.bottle_app.exception.user.UserIsNotVerifiedException;
import com.bottle_app.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
