package com.bottle_app.controller;

import com.bottle_app.dto.BottleRequestDto;
import com.bottle_app.dto.BottleResponseDto;
import com.bottle_app.dto.DefaultResponseDto;
import com.bottle_app.dto.PageResponseDto;
import com.bottle_app.model.User;
import com.bottle_app.service.BottleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Tag(name = "Bottle Controller", description = "유리병과 관련된 서비스를 제공")
@RequestMapping("/bottles")
public class BottleController {

    private static final Logger log = LoggerFactory.getLogger(BottleController.class);
    @Autowired
    private BottleService bottleService;

    //get all bottles arrive at currently logging in user
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "현재 로그인한 유저에게 도착한 모든 유리병을 표시")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "JWT 토큰 인증 실패",
                content = @Content(schema = @Schema(implementation = DefaultResponseDto.class))),
    })
    public PageResponseDto getAllBottles(@RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "10") int size,
                                         @AuthenticationPrincipal User user){

        return bottleService.getBottleByReceiver(user, page, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "유리병 id를 이용하여 유리병 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "JWT 토큰 인증 실패",
                    content = @Content(schema = @Schema(implementation = DefaultResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유리병 id",
                    content = @Content(schema = @Schema(implementation = DefaultResponseDto.class))),
    })
    public BottleResponseDto getBottleById(@PathVariable("id") long id){
        return bottleService.getBottleById(id);
    }

    @PostMapping("/post")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "새로운 유리병을 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "JWT 토큰 인증 실패",
                    content = @Content(schema = @Schema(implementation = DefaultResponseDto.class))),
    })
    public BottleResponseDto createBottle(@RequestBody BottleRequestDto bottleRequestDto, @AuthenticationPrincipal User user){
        log.info("Current user: {}", user);
        return bottleService.createBottle(user, bottleRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(summary = "유리병 id를 이용하여 유리병 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "JWT 토큰 인증 실패",
                    content = @Content(schema = @Schema(implementation = DefaultResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유리병 id",
                    content = @Content(schema = @Schema(implementation = DefaultResponseDto.class))),
    })
    public void deleteBottleById(@PathVariable("id") long bottleid){
        bottleService.deleteBottleById(bottleid);
    }
}
