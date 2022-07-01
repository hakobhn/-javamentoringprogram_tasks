package com.decathlon.sports.dao.repository;

import com.decathlon.sports.dao.AbstractRepository;
import com.decathlon.sports.dao.model.Sport;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SportRepository extends AbstractRepository<Sport, Integer> {

    /**
     * Derived query selecting by {@code name}. {@code name} uses deferred resolution that does not require
     * blocking to obtain the parameter value. String query selecting one entity.
     *
     * @param name
     * @return
     */
    Mono<Sport> findByNameIgnoreCase(Mono<String> name);

    /**
     * Derived query selecting by {@code name}. {@code name} uses deferred resolution that does not require
     * blocking to obtain the parameter value. String query selecting all entities with name containing query.
     *
     * @param name
     * @return
     */
    @Query("{'name': {$regex : ?0, $options: 'i'}}")
    Flux<Sport> findByLikeName(Mono<String> name);

    /**
     * String query selecting one entity.
     *
     * @param description
     * @return
     */
    @Query("{'description': {$regex : ?0, $options: 'i'}}")
    Flux<Sport> findByDescriptionRegex(Mono<String> description);


    /**
     * Use a tailable cursor to emit a stream of entities as new entities are written to the capped collection.
     *
     * @return
     */
    @Tailable
    Flux<Sport> findWithTailableCursorBy();


    @Aggregation(pipeline = { "{$group: { _id: '', total: {$max: $id }}}" })
    public Mono<Integer> maxId();
}
