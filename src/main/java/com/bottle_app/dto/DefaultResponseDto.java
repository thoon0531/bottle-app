package com.bottle_app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "Global_Res : 응답 코드 메시지 DTO")
public class DefaultResponseDto {
    @Schema(description = "응답 메시지")
    private String message;

    public DefaultResponseDto(String message) {
        this.message = message;
    }
}
