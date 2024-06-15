package com.bottle_app.service;

import com.bottle_app.dto.BottleRequestDto;
import com.bottle_app.dto.PageResponseDto;
import com.bottle_app.model.Bottle;
import com.bottle_app.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface BottleService {
    Bottle createBottle(User user, BottleRequestDto bottleRequestDto);

    Optional<Bottle> getBottleById(long bottleid);

    PageResponseDto getBottleByReceiver(User user, int page, int size);

    void updateBottle(long bottleid, Bottle bottle);

    void deleteBottleById(long bottleid);

}
