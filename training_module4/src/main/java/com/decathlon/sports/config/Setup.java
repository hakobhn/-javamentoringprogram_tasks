package com.decathlon.sports.config;

import com.decathlon.sports.converter.SportDtoToEntityConverter;
import com.decathlon.sports.dao.model.Sport;
import com.decathlon.sports.dao.repository.SportRepository;
import com.decathlon.sports.dto.SportDTO;
import com.decathlon.sports.dto.SportFullDataDTO;
import com.decathlon.sports.service.SportConsumerService;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.reactivex.internal.operators.observable.ObservableBlockingSubscribe.subscribe;

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

    @PostConstruct
    public void etl() {
        Mono<SportFullDataDTO> response = sportConsumerService.fetchSports();
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
//                .thenMany(sportRepository.findAll())
//                .subscribe(sport -> System.out.println("saving " + sport.toString()));
    }

////    @PostConstruct
//    public void etlWithBackpressure() {
//        Mono<SportFullDataDTO> response = sportConsumerService.fetchSports();
//        SportFullDataDTO sports = response.block();
//
//        Flux.just(IntStream.range(0, 10))
//                .log()
//                .subscribe(new Subscriber<Integer>() {
//                    private Subscription s;
//                    int onNextAmount;
//
//                    @Override
//                    public void onSubscribe(Subscription s) {
//                        this.s = s;
//                        s.request(2);
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//
//                        onNextAmount++;
//                        if (onNextAmount % 2 == 0) {
//                            s.request(2);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });
//    }
}
