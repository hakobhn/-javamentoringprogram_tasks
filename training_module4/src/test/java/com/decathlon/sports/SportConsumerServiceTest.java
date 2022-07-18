package com.decathlon.sports;

import com.decathlon.sports.service.SportConsumerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SportConsumerServiceTest {

    @Autowired
    private SportConsumerService sportConsumerService;

    @Test
    void shouldReturnAllSportsAsList() throws Exception {

        assertThat(sportConsumerService.fetchSports()).isNotNull();
    }

    @Test
    void shouldReturnAllSportsDataObject() throws Exception {

        assertThat(sportConsumerService.fetchSportsAsWhole()).isNotNull();
    }
}
