package com.app.controller;

import com.app.model.Person;
import com.app.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public Flux<Person> getAll() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Person> findOne(@PathVariable Long id) {
        return personService.findOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Person> add(@RequestBody Person person) {
        return personService.save(person);
    }

}
