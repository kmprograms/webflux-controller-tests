package com.app.service;

import com.app.model.Person;
import com.app.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public Flux<Person> findAll() {
        return personRepository.findAll();
    }

    public Mono<Person> findOne(Long id) {
        return personRepository.findOne(id);
    }

    public Mono<Person> save(Person person) {
        return personRepository.save(person);
    }
}
