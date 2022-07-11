package com.decathlon.sports.service;

import com.decathlon.sports.dao.model.Sport;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SportService {

    Mono<Sport> findByName(String name);

    Flux<Sport> findByLikeName(String name);

    Mono<Object> create(String name);

    Flux<Sport> getAll();

}
