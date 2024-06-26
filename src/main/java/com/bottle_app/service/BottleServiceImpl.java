package com.bottle_app.service;

import com.bottle_app.dto.BottleRequestDto;
import com.bottle_app.dto.BottleResponseDto;
import com.bottle_app.dto.PageResponseDto;
import com.bottle_app.exception.bottle.BottleNotFoundException;
import com.bottle_app.model.Bottle;
import com.bottle_app.model.User;
import com.bottle_app.repository.BottleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BottleServiceImpl implements BottleService{

    private static final Logger log = LoggerFactory.getLogger(BottleServiceImpl.class);
    @Autowired
    private BottleRepository bottleRepository;
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public BottleResponseDto createBottle(User user, BottleRequestDto bottleRequestDto) {
        Bottle bottle = bottleRequestDto.toEntity();
        bottle.setCreatedAt(new Date());
        //for defend LazyInitializationException
        user = userService.selectUser(user.getId());

        //creator=currently logging in user
        user.addCreated(bottle);
        //receiver=select random user in DB
        Optional<User> userOptional = userService.getRandomUserExcludeSelf(user);
        userOptional.ifPresent(value -> value.addReceived(bottle));

        Bottle bottleRes = bottleRepository.save(bottle);

        return BottleResponseDto.builder()
                .id(bottleRes.getId())
                .title(bottleRes.getTitle())
                .content(bottleRes.getContent())
                .createdAt(bottleRes.getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public BottleResponseDto getBottleById(long id) {

        Bottle bottle = bottleRepository.findById(id).orElseThrow(
                () -> new BottleNotFoundException(String.format("No bottle with id %s is found", id))
        );
        return BottleResponseDto.builder()
                .id(bottle.getId())
                .title(bottle.getTitle())
                .content(bottle.getContent())
                .createdAt(bottle.getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public PageResponseDto getBottleByReceiver(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<BottleResponseDto> bottlePage = bottleRepository.findByReceiver(user, pageable).map(BottleResponseDto::entityToDto);

        //log.info(bottlePage.getContent().get(0).toString());

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
    @Transactional
    public BottleResponseDto updateBottle(long bottleid, Bottle bottle) {
        Bottle dbBottle = bottleRepository.findById(bottleid).orElseThrow(
                () -> new BottleNotFoundException(String.format("No bottle with id %s is found", bottleid))
        );
        dbBottle.setContent(bottle.getContent());
        dbBottle.setTitle(bottle.getTitle());
        dbBottle.setCreatedAt(bottle.getCreatedAt());
        bottleRepository.save(dbBottle);
        return BottleResponseDto.builder()
                .id(dbBottle.getId())
                .title(dbBottle.getTitle())
                .content(dbBottle.getContent())
                .createdAt(dbBottle.getCreatedAt()).build();
    }

    @Override
    @Transactional
    public void deleteBottleById(long bottleid) {
        bottleRepository.findById(bottleid).orElseThrow(
                () -> new BottleNotFoundException(String.format("No bottle with id %s is found", bottleid))
        );
        bottleRepository.deleteById(bottleid);
    }
}
