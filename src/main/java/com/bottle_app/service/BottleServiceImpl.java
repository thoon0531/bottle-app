package com.bottle_app.service;

import com.bottle_app.model.Bottle;
import com.bottle_app.repository.BottleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BottleServiceImpl implements BottleService{

    @Autowired
    private BottleRepository bottleRepository;

    @Override
    public Bottle createBottle(Bottle bottle) {
        return bottleRepository.save(bottle);
    }

    @Override
    public Optional<Bottle> getBottleById(long bottleid) {
        return bottleRepository.findById(bottleid);
    }

    @Override
    //TO DO
    public Iterable<Bottle> getBottleByReceiver(long receiverid) {
        return null;
    }

    @Override
    public void updateBottle(long bottleid, Bottle bottle) {
        bottleRepository.findById(bottleid).ifPresent(dbBottle -> {
            dbBottle.setContent(bottle.getContent());
            dbBottle.setTitle(bottle.getTitle());
            dbBottle.setCreated_at(bottle.getCreated_at());
            bottleRepository.save(dbBottle);
        });
    }

    @Override
    public void deleteBottleById(long bottleid) {
        bottleRepository.deleteById(bottleid);
    }
}
