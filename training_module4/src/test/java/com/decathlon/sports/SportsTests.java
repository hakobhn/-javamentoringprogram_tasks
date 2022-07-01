package com.decathlon.sports;

import com.decathlon.sports.dao.model.Sport;
import com.decathlon.sports.dao.repository.SportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class SportsTests {
    @Autowired
    private SportRepository repository;

    Sport given;

    @BeforeEach
    private void setUp() {
        given = new Sport();
        given.setId(123);
        given.setName("Test name");
        given.setDescription("Test description bla bal");
        given.setSlug("Test slug");
    }

    @Test
    public void givenName_whenFindName_thenFindSport() {
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
        repository.save(given).block();

        String term = "descript";
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
                .verifyComplete();
    }

    @Test
    public void givenSport_whenSave_thenSaveSport() {
        Mono<Sport> sportMono = repository.save(given);

        StepVerifier
                .create(sportMono)
                .assertNext(sport -> assertNotNull(sport.getId()))
                .expectComplete()
                .verify();
    }
}
