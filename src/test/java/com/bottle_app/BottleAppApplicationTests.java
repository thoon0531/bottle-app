package com.bottle_app;

import com.bottle_app.model.Bottle;
import com.bottle_app.model.User;
import com.bottle_app.repository.BottleRepository;
import com.bottle_app.repository.UserRepository;
import com.bottle_app.service.BottleService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
class BottleAppApplicationTests {

    @Autowired
    BottleService bottleService;
	@Autowired
	BottleRepository bottleRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
	void createBottles() {
        //create test
        Bottle bottle;
        User user = User.builder().username("aaa")
                .email("aaaa@bbb.com")
                .password("password")
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        User user2 = User.builder().username("bbb")
                .email("bbbb@ccc.com")
                .password("p@ssword")
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        Bottle saved = null;
        User savedUser = userRepository.save(user);
        savedUser = userRepository.save(user2);
        for (int i = 0; i < 10; i++) {
            bottle = Bottle.builder().title("asdagsdfdsfa")
                    .content("gfsoigjaojfkkdbvskfsfkaudkauda").createdAt(new Date()).build();
            user.addCreated(bottle);
            user2.addReceived(bottle);
            saved = bottleRepository.save(bottle);
        }

        for (int i = 0; i < 10; i++) {
            bottle = Bottle.builder().title("hgdfhasfqacasdasda")
                    .content("asdfdgfdd8999999966666666643").createdAt(new Date()).build();
            user2.addCreated(bottle);
            user.addReceived(bottle);
            saved = bottleRepository.save(bottle);
        }

        Assertions.assertThat(bottleRepository.count()).isEqualTo(20);
        Assertions.assertThat(userRepository.count()).isEqualTo(2);

        //read
        Optional<Bottle> op = bottleRepository.findById(saved.getId());
        Assertions.assertThat(op.isPresent()).isTrue();
        Assertions.assertThat(op.get().getId()).isEqualTo(saved.getId());

        //update
        //bottle = Bottle.builder().title("updatedTitle")
              //  .content("updated").createdAt(new Date()).build();
        //Bottle updated = bottleService.updateBottle(2L, bottle);
        //Assertions.assertThat(updated.getTitle()).isEqualTo("updatedTitle");


        //delete
        bottleRepository.deleteById(1L);
        Assertions.assertThat(bottleRepository.count()).isEqualTo(19);
    }

}
