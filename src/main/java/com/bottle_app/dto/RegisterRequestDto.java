package com.bottle_app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Schema(title = "Register_Req : 회원가입 요청 DTO")
public class RegisterRequestDto {
    @Schema(description = "유저 id")
    private String name;
    @Schema(description = "유저 비밀번호")
    private String password;
    @Email
    @Schema(description = "유저 이메일", example = "test@gmail.com")
    private String email;
    @Schema(description = "비밀번호 확인")
    private String passwordConfirm;

}
