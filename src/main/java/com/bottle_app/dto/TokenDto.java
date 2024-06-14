package com.bottle_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDto {
    private String access_token;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private String token_type;
}
