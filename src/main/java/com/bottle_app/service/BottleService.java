package com.bottle_app.service;

import com.bottle_app.model.Bottle;

import java.util.Optional;

public interface BottleService {
    Bottle createBottle(Bottle bottle);

    Optional<Bottle> getBottleById(long bottleid);

    Iterable<Bottle> getBottleByReceiver(long receiverid);

    void updateBottle(long bottleid, Bottle bottle);

    void deleteBottleById(long bottleid);

}
