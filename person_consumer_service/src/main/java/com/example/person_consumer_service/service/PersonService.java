package com.example.person_consumer_service.service;

import com.example.person_consumer_service.entity.Person;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Service
public class PersonService {

    private final WebClient webClient;

    public PersonService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Person> getAllPersons() {
        Person[] array = webClient.get()
                .retrieve()
                .bodyToMono(Person[].class) // non-blocking
                .block(); // convert to synchronous for now
        return Arrays.asList(array);
    }

    public Person getPersonById(Integer id) {
        return webClient.get()
                .uri("/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Person.class)
                .block();
    }

    public Person createPerson(Person person) {
        return webClient.post()
                .bodyValue(person)
                .retrieve()
                .bodyToMono(Person.class)
                .block();
    }

    public Person updatePerson(Integer id, Person personDetails) {
        return webClient.put()
                .uri("/{id}", id)
                .bodyValue(personDetails)
                .retrieve()
                .bodyToMono(Person.class)
                .block();
    }

    public void deletePerson(Integer id) {
        webClient.delete()
                .uri("/{id}", id)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}
