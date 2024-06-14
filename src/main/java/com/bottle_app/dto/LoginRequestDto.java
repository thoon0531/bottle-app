package com.bottle_app.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String name;
    private String password;
}
