package com.decathlon.sports.config;

import com.decathlon.sports.converter.SportDtoToEntityConverter;
import com.decathlon.sports.dao.repository.SportRepository;
import com.decathlon.sports.dto.SportDTO;
import com.decathlon.sports.dto.SportFullDataDTO;
import com.decathlon.sports.service.SportConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Component
public class Setup {

    private static final Logger logger = LoggerFactory.getLogger(Setup.class);

    private final SportRepository sportRepository;
    private final SportConsumerService sportConsumerService;
    private final SportDtoToEntityConverter sportDtoToEntityConverter;

    public Setup(SportRepository sportRepository, SportConsumerService sportConsumerService,
                 SportDtoToEntityConverter sportDtoToEntityConverter) {
        this.sportRepository = sportRepository;
        this.sportConsumerService = sportConsumerService;
        this.sportDtoToEntityConverter = sportDtoToEntityConverter;
    }

    //    @PostConstruct
    public void etl() {
        Mono<SportFullDataDTO> response = sportConsumerService.fetchSportsAsWhole();
        SportFullDataDTO sports = response.block();

        sportRepository
                .deleteAll()
                .thenMany(
                        Flux
                                .just(Optional.ofNullable(sports).map(SportFullDataDTO::getData))
                                .map(data -> data.orElse(new ArrayList<>()).stream()
                                        .map(sportDtoToEntityConverter::convert)
                                        .map(sportRepository::save)
                                        .map(entity -> {
                                            entity.flux().subscribe(sport -> logger.info(sport.toString()));
                                            return entity;
                                        })
                                        .collect(Collectors.toList()))
                ).blockLast();
    }

    @PostConstruct
    public void etlWithBackpressure() {
        Flux<List<SportDTO>> response = sportConsumerService.fetchSports();

        sportRepository
                .deleteAll()
                .thenMany(
                        response
                                .log("category", Level.ALL, SignalType.ON_NEXT, SignalType.ON_ERROR)
                                .limitRate(20)
                                .delayElements(Duration.ofMillis(1000))
                                .doOnNext(data -> logger.debug("On next data: {}", data))
                                .map(data -> data.stream()
                                        .map(dt -> {
                                            logger.debug("Date: {}", dt);
                                            return dt;
                                        })
                                        .map(sportDtoToEntityConverter::convert)
                                        .map(sportRepository::save)
                                        .map(entity -> {
                                            entity.flux().subscribe(sport -> logger.debug(sport.toString()));
                                            return entity;
                                        })
                                        .collect(Collectors.toList()))
                ).blockLast();
    }
}
