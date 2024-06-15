package com.bottle_app.controller;

import com.bottle_app.dto.BottleRequestDto;
import com.bottle_app.dto.PageResponseDto;
import com.bottle_app.model.Bottle;
import com.bottle_app.model.User;
import com.bottle_app.service.BottleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public PageResponseDto getAllBottles(@RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "10") int size,
                                         @AuthenticationPrincipal User user){

        return bottleService.getBottleByReceiver(user, page, size);
    }

    @GetMapping("/{id}")
    public Optional<Bottle> getBottleById(@PathVariable("id") long bottleid){
        return bottleService.getBottleById(bottleid);
    }

    @PostMapping("/post")
    public Bottle createBottle(@RequestBody BottleRequestDto bottleRequestDto, @AuthenticationPrincipal User user){
        log.info("Current user: {}", user);
        return bottleService.createBottle(user, bottleRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    void deleteBottleById(@PathVariable("id") long bottleid){
        bottleService.deleteBottleById(bottleid);
    }
}
