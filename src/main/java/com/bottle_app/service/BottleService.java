package com.bottle_app.service;

import com.bottle_app.dto.PageResponseDto;
import com.bottle_app.model.Bottle;
import com.bottle_app.model.User;

import java.util.Optional;

public interface BottleService {
    Bottle createBottle(Bottle bottle);

    Optional<Bottle> getBottleById(long bottleid);

    PageResponseDto getBottleByReceiver(User user, int page, int size);

    void updateBottle(long bottleid, Bottle bottle);

    void deleteBottleById(long bottleid);

}
