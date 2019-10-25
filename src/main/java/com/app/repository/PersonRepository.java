package com.app.repository;

import com.app.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository {
    Flux<Person> findAll();
    Mono<Person> findOne(Long id);
    Mono<Person> save(Person person);
}
