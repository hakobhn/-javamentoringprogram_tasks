package com.decathlon.sports;

import com.decathlon.sports.controller.SportResource;
import com.decathlon.sports.dao.model.Sport;
import com.decathlon.sports.service.SportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = SportResource.class)
class SportsResurceTest {

    @MockBean
    SportService sportService;
    @Autowired
    private WebTestClient webClient;

    @Test
    void shouldReturnFoundSport() throws Exception {

        Sport sport = new Sport();
        sport.setId(123);
        sport.setName("Test name");
        sport.setDescription("Test unique_description bla bal");
        sport.setSlug("Test slug");

        Flux<Sport> sports = Flux.just(sport);

        Mockito.when(sportService.getAll()).thenReturn(sports);

        this.webClient.get()
                .uri(SportResource.API_V_1_SPORT+"/search?q=Bouldering")
                .exchange()
                .expectStatus().isOk();
    }
}
