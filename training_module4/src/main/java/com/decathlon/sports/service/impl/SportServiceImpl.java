package com.decathlon.sports.service.impl;

import com.decathlon.sports.dao.model.Sport;
import com.decathlon.sports.dao.repository.SportRepository;
import com.decathlon.sports.exception.NotFoundException;
import com.decathlon.sports.exception.ResourceAlreadyExists;
import com.decathlon.sports.service.SportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SportServiceImpl implements SportService {

    Logger logger = LoggerFactory.getLogger(SportServiceImpl.class);

    private final SportRepository sportRepository;

    public SportServiceImpl(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    public Mono<Sport> findByName(String name) {
        Mono<Sport> fallback = Mono.error(new NotFoundException("Sport with name " + name + " not found"));
        return sportRepository.findByNameIgnoreCase(Mono.just(name)).switchIfEmpty(fallback);
    }

    public Flux<Sport> findByLikeName(String name) {
        return sportRepository.findByLikeName(Mono.just(name));
    }

    public Mono<Object> create(String name) {
        Mono<Sport> fallback = Mono.error(new ResourceAlreadyExists("Sport with name " + name + " already exists"));
//        sportRepository.findByNameIgnoreCase(Mono.just(name)).switchIfEmpty(fallback);

        Mono<Boolean> monoPresent = sportRepository.findByNameIgnoreCase(Mono.just(name)).hasElement();
        return monoPresent.map(
                isAvailable -> isAvailable ? fallback.block() :
                        sportRepository.maxId()
                                .subscribe(id -> {
                                            Sport sport = new Sport();
                                            sport.setId(++id);
                                            sport.setName(name);
                                            sportRepository.save(sport);
                                        }
                                ));
    }

    public Flux<Sport> getAll() {
        return sportRepository.findAll();
    }

}
