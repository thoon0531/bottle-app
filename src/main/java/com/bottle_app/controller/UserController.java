package com.bottle_app.controller;

import com.bottle_app.dto.DefaultResponseDto;
import com.bottle_app.dto.LoginRequestDto;
import com.bottle_app.dto.RegisterRequestDto;
import com.bottle_app.dto.TokenDto;
import com.bottle_app.event.UserRegistrationEvent;
import com.bottle_app.model.User;
import com.bottle_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@Tag(name = "User Controller", description = "유저 로그인, 회원가입 등과 같은 서비스를 제공")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    //register
    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "요청 값 검증 에러(이메일 형식 맞지 않음, 비밀번호 확인 맞지 않음)",
                    content = @Content(schema = @Schema(implementation = DefaultResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "사용자가 이미 존재(id, 이메일 중복)",
                    content = @Content(schema = @Schema(implementation = DefaultResponseDto.class))),
    })
    public DefaultResponseDto register(@RequestBody RegisterRequestDto registerRequestDto) {
        User user = userService.createUser(registerRequestDto);
        eventPublisher.publishEvent(new UserRegistrationEvent(user));

        return new DefaultResponseDto("User registered successfully");
    }

    //login
    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "비밀번호 불일치, 인증되지 않은 이메일",
                    content = @Content(schema = @Schema(implementation = DefaultResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자 id",
                    content = @Content(schema = @Schema(implementation = DefaultResponseDto.class))),
    })
    public TokenDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto);
    }

    //email verifying
    @GetMapping("/verify/email")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "이메일 인증")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "존재하지 않는 인증 링크",
                    content = @Content(schema = @Schema(implementation = DefaultResponseDto.class))),
    })
    public DefaultResponseDto verifyEmail(@RequestParam String id) {
        byte[] actualId = Base64.getDecoder().decode(id.getBytes());
        String username = userService.getUsernameForVerificationId(new String(actualId));

        User user = userService.findByUsername(username);
        user.setVerified(true);
        userService.save(user);
        return new DefaultResponseDto("User verified successfully");
    }

    //regenerate tokens
    @PostMapping("/reissue")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "JWT 토큰 재발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "JWT 토큰 유효하지 않음",
                    content = @Content(schema = @Schema(implementation = DefaultResponseDto.class))),
    })
    public TokenDto reissue(@RequestHeader("RefreshToken") String refreshToken) {
        return userService.reissueTokens(refreshToken);
    }
}
