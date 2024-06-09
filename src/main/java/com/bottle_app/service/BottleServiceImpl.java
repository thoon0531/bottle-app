package com.bottle_app.service;

import com.bottle_app.dto.BottleResponseDto;
import com.bottle_app.dto.PageResponseDto;
import com.bottle_app.model.Bottle;
import com.bottle_app.model.User;
import com.bottle_app.repository.BottleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public PageResponseDto getBottleByReceiver(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<BottleResponseDto> bottlePage = bottleRepository.findAll(pageable).map(BottleResponseDto::entityToDto);

        return PageResponseDto.builder()
                .bottles(bottlePage.getContent())
                .pageNo(page)
                .pageSize(size)
                .totalElements(bottlePage.getTotalElements())
                .totalPages(bottlePage.getTotalPages())
                .last(bottlePage.isLast())
                .build();
    }

    @Override
    public void updateBottle(long bottleid, Bottle bottle) {
        bottleRepository.findById(bottleid).ifPresent(dbBottle -> {
            dbBottle.setContent(bottle.getContent());
            dbBottle.setTitle(bottle.getTitle());
            dbBottle.setCreatedAt(bottle.getCreatedAt());
            bottleRepository.save(dbBottle);
        });
    }

    @Override
    public void deleteBottleById(long bottleid) {
        bottleRepository.deleteById(bottleid);
    }
}
