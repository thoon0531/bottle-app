package com.bottle_app.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String name;
    private String password;
    private String email;
    private String passwordConfirm;

}
