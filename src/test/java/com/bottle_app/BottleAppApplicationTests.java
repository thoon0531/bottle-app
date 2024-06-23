package com.bottle_app;

import com.bottle_app.dto.BottleRequestDto;
import com.bottle_app.model.Bottle;
import com.bottle_app.model.Role;
import com.bottle_app.model.User;
import com.bottle_app.repository.BottleRepository;
import com.bottle_app.repository.UserRepository;
import com.bottle_app.service.BottleService;
import org.assertj.core.api.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
class BottleAppApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(BottleAppApplicationTests.class);
    @Autowired
    BottleService bottleService;
	@Autowired
	BottleRepository bottleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Transactional
	void createBottlesOneUser() {
        Date now = new Date();
        BottleRequestDto req = new BottleRequestDto();
        req.setTitle("Test Bottle");
        req.setContent("Test Content");

        //one user(only creator)
        User user01 = User.builder()
                .username("user01")
                .email("aaa@bbb.com")
                .password(passwordEncoder.encode("password"))
                .createdAt(now)
                .updatedAt(now)
                .role(Role.USER)
                .verified(true)
                .randId((long)(Math.random()*1000000))
                .lastBottleCreation(null)
                .build();
        userRepository.save(user01);

        bottleService.createBottle(user01, req);
        //expected receiver is null
        Assertions.assertThat(bottleRepository.findAll()).hasSize(1);
        Optional<Bottle> op = bottleRepository.findById(1L);
        Assertions.assertThat(op.isPresent()).isTrue();
        Assertions.assertThat(op.get().getTitle().equals("Test Bottle")).isTrue();
        Assertions.assertThat(op.get().getCreator().getUsername().equals("user01")).isTrue();
        Assertions.assertThat(op.get().getReceiver()).isNull();

        op.get().getCreator().setCreated(null); //remove relation
        bottleRepository.deleteAll(); //so delete query works fine
        Assertions.assertThat(bottleRepository.findAll()).hasSize(0);
    }

    @Test
    @Transactional
    void createBottlesTwoUsers() {
        Date now = new Date();
        BottleRequestDto req = new BottleRequestDto();
        req.setTitle("Test Bottle");
        req.setContent("Test Content");
        Optional<Bottle> op;

        //two user
        User user01 = User.builder()
                .username("user01")
                .email("aaa@bbb.com")
                .password(passwordEncoder.encode("password"))
                .createdAt(now)
                .updatedAt(now)
                .role(Role.USER)
                .verified(true)
                .randId((long)(Math.random()*1000000))
                .lastBottleCreation(null)
                .build();
        User user02 = User.builder()
                .username("user02")
                .email("bbb@bbb.com")
                .password(passwordEncoder.encode("password"))
                .createdAt(now)
                .updatedAt(now)
                .role(Role.USER)
                .verified(true)
                .randId((long)(Math.random()*1000000))
                .lastBottleCreation(null)
                .build();
        userRepository.save(user01);
        userRepository.save(user02);

        //expected receiver is other user
        bottleService.createBottle(user02, req);
        Assertions.assertThat(bottleRepository.findAll()).hasSize(1);
        op = bottleRepository.findById(2L);
        Assertions.assertThat(op.isPresent()).isTrue();
        Assertions.assertThat(op.get().getTitle().equals("Test Bottle")).isTrue();
        Assertions.assertThat(op.get().getCreator().getUsername().equals("user02")).isTrue();
        Assertions.assertThat(op.get().getReceiver().getUsername().equals("user01")).isTrue();

        bottleService.createBottle(user01, req);
        Assertions.assertThat(bottleRepository.findAll()).hasSize(2);
        op = bottleRepository.findById(3L);
        Assertions.assertThat(op.isPresent()).isTrue();
        Assertions.assertThat(op.get().getTitle().equals("Test Bottle")).isTrue();
        Assertions.assertThat(op.get().getCreator().getUsername().equals("user01")).isTrue();
        Assertions.assertThat(op.get().getReceiver().getUsername().equals("user02")).isTrue();
    }

    @Test
    @Transactional
    void createBottlesThreeUsers() {
        Date now = new Date();
        BottleRequestDto req = new BottleRequestDto();
        req.setTitle("Test Bottle");
        req.setContent("Test Content");
        Optional<Bottle> op;

        //two user
        User user01 = User.builder()
                .username("user01")
                .email("aaa@bbb.com")
                .password(passwordEncoder.encode("password"))
                .createdAt(now)
                .updatedAt(now)
                .role(Role.USER)
                .verified(true)
                .randId((long)(Math.random()*1000000))
                .lastBottleCreation(null)
                .build();
        User user02 = User.builder()
                .username("user02")
                .email("bbb@bbb.com")
                .password(passwordEncoder.encode("password"))
                .createdAt(now)
                .updatedAt(now)
                .role(Role.USER)
                .verified(true)
                .randId((long)(Math.random()*1000000))
                .lastBottleCreation(null)
                .build();
        User user03 = User.builder()
                .username("user03")
                .email("ccc@bbb.com")
                .password(passwordEncoder.encode("password"))
                .createdAt(now)
                .updatedAt(now)
                .role(Role.USER)
                .verified(true)
                .randId((long)(Math.random()*1000000))
                .lastBottleCreation(null)
                .build();
        userRepository.save(user01);
        userRepository.save(user02);
        userRepository.save(user03);

        //expected receiver is user02 or user03
        long bottleId = 4L;
        for(int i = 0; i < 10; i++){
            bottleService.createBottle(user01, req);
            op = bottleRepository.findById(bottleId++);
            Assertions.assertThat(op.isPresent()).isTrue();
            Assertions.assertThat(op.get().getTitle().equals("Test Bottle")).isTrue();
            Assertions.assertThat(op.get().getCreator().getUsername().equals("user01")).isTrue();
            log.info(op.get().toString());

            Condition<Optional<Bottle>> eq1 = new Condition<>(b -> b.get().getReceiver().getUsername().equals("user02"), "user02");
            Condition<Optional<Bottle>> eq2 = new Condition<>(b -> b.get().getReceiver().getUsername().equals("user03"), "user03");
            Assertions.assertThat(op).is(Assertions.anyOf(eq1, eq2));
        }
    }

}
