package com.bottle_app.controller;

import com.bottle_app.dto.BottleRequestDto;
import com.bottle_app.dto.BottleResponseDto;
import com.bottle_app.dto.DefaultResponseDto;
import com.bottle_app.dto.PageResponseDto;
import com.bottle_app.model.Bottle;
import com.bottle_app.model.User;
import com.bottle_app.service.BottleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/bottles")
public class BottleController {

    private static final Logger log = LoggerFactory.getLogger(BottleController.class);
    @Autowired
    private BottleService bottleService;

    //get all bottles arrive at currently logging in user
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public PageResponseDto getAllBottles(@RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "10") int size,
                                         @AuthenticationPrincipal User user){

        return bottleService.getBottleByReceiver(user, page, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public BottleResponseDto getBottleById(@PathVariable("id") long id){
        return bottleService.getBottleById(id);
    }

    @PostMapping("/post")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Bottle createBottle(@RequestBody BottleRequestDto bottleRequestDto, @AuthenticationPrincipal User user){
        log.info("Current user: {}", user);
        return bottleService.createBottle(user, bottleRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public DefaultResponseDto deleteBottleById(@PathVariable("id") long bottleid){
        bottleService.deleteBottleById(bottleid);
        return new DefaultResponseDto("Bottle deleted");
    }
}
