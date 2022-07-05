package com.decathlon.sports.controller;

import com.decathlon.sports.dao.model.Sport;
import com.decathlon.sports.service.SportService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(SportResource.API_V_1_SPORT)
public class SportResource {
    public static final String API_V_1_SPORT = "/api/v1/sport/";

    private final SportService sportService;

    public SportResource(SportService sportService) {
        this.sportService = sportService;
    }

    /**
     * Search sports with name like to term
     *
     * @param q term for search by name
     * @return
     */
    @GetMapping("search")
    public Flux<Sport> searchByName(@RequestParam String q) {
        return sportService.findByLikeName(q);
    }

    /**
     * Find sport with provided name
     *
     * @param name find with this name
     * @return
     */
    @GetMapping("{name}")
    public Mono<Sport> findByName(@PathVariable String name) {
        return sportService.findByName(name);
    }

    @PostMapping("{sportName}")
    public Mono<?> addSport(@PathVariable("sportName") String name) {
        return sportService.create(name);
    }

    @GetMapping
    public Flux<Sport> getAll() {
        return sportService.getAll();
    }


}
