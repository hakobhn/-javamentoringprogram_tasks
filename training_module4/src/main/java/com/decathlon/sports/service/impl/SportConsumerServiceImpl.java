package com.decathlon.sports.service.impl;

import com.decathlon.sports.dto.SportFullDataDTO;
import com.decathlon.sports.service.SportConsumerService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class SportConsumerServiceImpl implements SportConsumerService {

    public static final String SPORTS_API = "https://sports.api.decathlon.com/sports";
    private final WebClient webClient;

    public SportConsumerServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<SportFullDataDTO> fetchSports() {
        return webClient.get()
                .uri(SPORTS_API)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(SportFullDataDTO.class);
    }
}
