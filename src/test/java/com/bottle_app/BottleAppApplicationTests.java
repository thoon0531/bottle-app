package com.bottle_app;

import com.bottle_app.model.Bottle;
import com.bottle_app.repository.BottleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class BottleAppApplicationTests {

	@Autowired
	BottleRepository bottleRepository;

	@Test
	void createBottles() {
        Bottle bottle;
        Bottle saved = null;
        for (int i = 0; i < 10; i++) {
            bottle = Bottle.builder().title("asdagsdfdsfa")
                    .content("gfsoigjaojfkkdbvskfsfkaudkauda").createdAt(new Date()).build();
            saved = bottleRepository.save(bottle);
        }
        Assertions.assertThat(saved.getTitle()).isEqualTo("asdagsdfdsfa");
    }

}
