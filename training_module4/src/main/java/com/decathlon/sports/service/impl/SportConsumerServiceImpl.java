package com.decathlon.sports.service.impl;

import com.decathlon.sports.dto.SportDTO;
import com.decathlon.sports.dto.SportFullDataDTO;
import com.decathlon.sports.service.SportConsumerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SportConsumerServiceImpl implements SportConsumerService {

    public static final String SPORTS_API = "https://sports.api.decathlon.com/sports";
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public SportConsumerServiceImpl(WebClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    public Mono<SportFullDataDTO> fetchSportsAsWhole() {
        return webClient.get()
                .uri(SPORTS_API)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(SportFullDataDTO.class);
    }

    public Flux<List<SportDTO>> fetchSports() {
        return webClient.get()
                .uri(SPORTS_API)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(JsonNode.class)
                .map(s -> s.path("data"))
                .map(s -> {
                    try {
                        return objectMapper.readValue(s.traverse(), new TypeReference<List<SportDTO>>() {
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                        return new ArrayList<SportDTO>();
                    }
                });
    }
}
