package com.decathlon.sports.service;

import com.decathlon.sports.dto.SportFullDataDTO;
import reactor.core.publisher.Mono;

public interface SportConsumerService {

    Mono<SportFullDataDTO> fetchSports();

}
