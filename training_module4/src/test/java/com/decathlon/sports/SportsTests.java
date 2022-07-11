package com.decathlon.sports;

import com.decathlon.sports.dao.model.Sport;
import com.decathlon.sports.dao.repository.SportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
class SportsTests {
    @Autowired
    private SportRepository repository;

    Sport given;

    @BeforeEach
    private void setUp() {
        given = new Sport();
        given.setId(123);
        given.setName("Test name");
        given.setDescription("Test unique_description bla bal");
        given.setSlug("Test slug");
    }

    @Test
    public void givenName_whenFindName_thenFindSport() {
        repository.deleteAll().block();
        repository.save(given).block();

        Mono<Sport> sportFlux = repository.findByNameIgnoreCase(Mono.just(given.getName()));

        StepVerifier
                .create(sportFlux)
                .assertNext(sport -> {
                    assertEquals(given.getId(), sport.getId());
                    assertEquals(given.getName() , sport.getName());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void givenDescription_whenFindFirstByDescription_thenFindSport() {
        repository.deleteAll().block();
        repository.save(given).block();

        given = new Sport();
        given.setId(1234);
        given.setName("Test name1");
        given.setDescription("Test unique_description1 bla bal");
        given.setSlug("Test slug1");

        repository.save(given).block();

        String term = "unique_descript";
        Flux<Sport> sportMono = repository
                .findByDescriptionRegex(Mono.just(term));


        StepVerifier
                .create(sportMono)
                .assertNext(sport -> {
                    assertTrue(sport.getDescription().toLowerCase().contains(term.toLowerCase()));
                })
                .assertNext(sport -> {
                    assertTrue(sport.getDescription().toLowerCase().contains(term.toLowerCase()));
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void givenSport_whenSave_thenSaveSport() {
        repository.deleteAll().block();
        Mono<Sport> sportMono = repository.save(given);

        StepVerifier
                .create(sportMono)
                .assertNext(sport -> assertNotNull(sport.getId()))
                .expectComplete()
                .verify();
    }
}
