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
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Component
public class Setup {

    private Logger logger = LoggerFactory.getLogger(Setup.class);

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
                                .just(sports.getData())
                                .map(data -> data.stream()
                                        .map(sportDtoToEntityConverter::convert)
                                        .map(sportRepository::save)
                                        .map(entity -> {
                                            entity.flux().subscribe(sport -> System.out.println(sport.toString()));
                                            return entity;
                                        })
                                        .collect(Collectors.toList()))
                ).blockLast();
    }

    @PostConstruct
    public void etlWithBackpressure() {
        Flux<List<SportDTO>> response = sportConsumerService.fetchSports();
//        SportFullDataDTO sports = response.block();

//        Flux.range(1, 10)
//                .log("category", Level.ALL, SignalType.ON_NEXT, SignalType.ON_ERROR)
//                .limitRate(10, 0)
//                .delayElements(Duration.ofMillis(100))
//                .doOnNext(System.out::println)
//                .subscribe(System.out::println);

        sportRepository
                .deleteAll()
                .thenMany(
                        response
                                .log("category", Level.ALL, SignalType.ON_NEXT, SignalType.ON_ERROR)
                                .limitRate(20)
                                .delayElements(Duration.ofMillis(1000))
                                .doOnNext(data -> {
                                    System.out.println("On next data: " + data);
                                })
                                .map(data -> data.stream()
                                        .map(dt -> {
                                            System.out.println(dt);
                                            return dt;
                                        })
                                        .map(sportDtoToEntityConverter::convert)
                                        .map(sportRepository::save)
                                        .map(entity -> {
                                            entity.flux().subscribe(sport -> System.out.println(sport.toString()));
                                            return entity;
                                        })
                                        .collect(Collectors.toUnmodifiableList()))
                ).blockLast();
    }
}
