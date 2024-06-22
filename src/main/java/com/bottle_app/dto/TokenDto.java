package com.bottle_app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(title = "Tokens_Res : 로그인 응답 DTO")
public class TokenDto {
    @Schema(description = "엑세스 토큰")
    private String access_token;
    @Schema(description = "리프레쉬 토큰")
    private String refresh_token;
    @Schema(description = "엑세스 토큰 만료시간")
    private int expires_in;
    @Schema(description = "엑세스 토큰 범위")
    private String scope;
    @Schema(description = "jwt 토큰 타입")
    private String token_type;
    @Schema(description = "유저가 마지막으로 유리병 작성한 날짜", example = "2024-06-14 17:30", type = "string")
    private Date last_bottle_creation;
}
