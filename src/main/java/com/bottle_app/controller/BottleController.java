package com.bottle_app.controller;

import com.bottle_app.dto.PageResponseDto;
import com.bottle_app.model.Bottle;
import com.bottle_app.model.User;
import com.bottle_app.service.BottleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/bottles/")
public class BottleController {

    private static final Logger log = LoggerFactory.getLogger(BottleController.class);
    @Autowired
    private BottleService bottleService;

    //get all bottles arrive at currently logging in user
    @GetMapping
    public PageResponseDto getAllBottles(@RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "10") int size){
        //로그인한 유저의 정보를 받아와서 그 유저의 ID로 편지 찾는 기능을 구현해야 함
        //TO DO

        return bottleService.getBottleByReceiver(new User(), page, size);
    }

    @GetMapping("{id}")
    public Optional<Bottle> getBottleById(@PathVariable("id") long bottleid){
        return bottleService.getBottleById(bottleid);
    }

    @PostMapping("post")
    public Bottle createBottle(@RequestBody Bottle bottle){
        return bottleService.createBottle(bottle);
    }

    @DeleteMapping("delete/{id}")
    void deleteBottleById(@PathVariable("id") long bottleid){
        bottleService.deleteBottleById(bottleid);
    }
}
