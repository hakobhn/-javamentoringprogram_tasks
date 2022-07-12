package com.decathlon.sports;

import com.decathlon.sports.dao.model.Sport;
import com.decathlon.sports.dao.repository.SportRepository;
import com.decathlon.sports.service.SportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class SportsServiceTest {

    @Autowired
    SportRepository sportRepository;

    @Autowired
    SportService sportService;

    @Test
    void shouldAddNewSport() throws Exception {

        assertThat(sportService.create("Test name")).isNotNull();

        assertThat(sportService.create("Test name")).withFailMessage("Resource already exists");
    }

    @Test
    void shouldAddNewSportInMongo() throws Exception {

        Sport sport = new Sport();
        sport.setId(123);
        sport.setName("Test name");
        sport.setDescription("Test unique_description bla bal");
        sport.setSlug("Test slug");

        assertThat(sportRepository.save(sport)).isNotNull();
    }

}
