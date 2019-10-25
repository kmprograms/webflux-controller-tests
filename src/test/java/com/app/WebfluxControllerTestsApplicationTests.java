package com.app;

import com.app.controller.PersonController;
import com.app.model.Person;
import com.app.repository.PersonRepository;
import com.app.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(PersonController.class)
@Import(PersonService.class)
class WebfluxControllerTestsApplicationTests {

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void test1() {

        Mockito
                .when(personRepository.findAll())
                .thenReturn(Flux.just(Person.builder().id(1L).name("A").age(10).build()));

        webTestClient
                .get()
                .uri("/people")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                // .expectHeader().exists("Content-Type")
                .expectHeader().contentType(MediaType.APPLICATION_JSON)

                .expectBodyList(Person.class)
                .hasSize(1);

                /*.expectBody()
                .jsonPath("$[0].name").isEqualTo("A")
                .jsonPath("$[0].age").isEqualTo(10);*/
    }

    @Test
    void test2() {

        var person = Person.builder().id(1L).name("A").age(10).build();

        Mockito
                .when(personRepository.findOne(1L))
                .thenReturn(Mono.just(Person.builder().id(1L).name("A").age(10).build()));

        webTestClient
                .get()
                .uri("/people/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("A")
                .jsonPath("$.age").isEqualTo(10);
    }

    @Test
    void test3() {

        var person = Person.builder().id(1L).name("A").age(10).build();

        Mockito
                .when(personRepository.save(ArgumentMatchers.any(Person.class)))
                .thenReturn(Mono.just(person));

        webTestClient
                .post()
                .uri("/people")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(person))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.name").isEqualTo("A")
                .jsonPath("$.age").isEqualTo(10);
    }

}
