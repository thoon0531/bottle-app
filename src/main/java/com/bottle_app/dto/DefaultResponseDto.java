package com.bottle_app.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultResponseDto {
    private String message;

    public DefaultResponseDto(String message) {
        this.message = message;
    }
}
