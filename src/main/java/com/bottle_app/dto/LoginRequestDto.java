package com.bottle_app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "Login_Req : 로그인 요청 DTO")
public class LoginRequestDto {
    @Schema(description = "유저 id")
    private String name;
    @Schema(description = "유저 비밀번호")
    private String password;
}
