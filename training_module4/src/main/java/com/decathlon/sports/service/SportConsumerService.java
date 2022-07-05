package com.decathlon.sports.service;

import com.decathlon.sports.dto.SportDTO;
import com.decathlon.sports.dto.SportFullDataDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface SportConsumerService {

    Mono<SportFullDataDTO> fetchSportsAsWhole();

    Flux<List<SportDTO>> fetchSports();

}
