package com.decathlon.sports.dao;


import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.io.Serializable;

@NoRepositoryBean
public interface AbstractRepository<E, S extends Serializable> extends ReactiveCrudRepository<E, S> {

}
