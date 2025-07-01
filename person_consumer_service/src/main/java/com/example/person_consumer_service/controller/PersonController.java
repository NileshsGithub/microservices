package com.example.person_consumer_service.controller;

import com.example.person_consumer_service.entity.Person;
import com.example.person_consumer_service.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getAllPersons(){
        return personService.getAllPersons();
    }

    @GetMapping(value = "/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public Person getPersonById(@PathVariable Integer id){
        return personService.getPersonById(id);
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person created = personService.createPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(
            @PathVariable Integer id,
            @RequestBody Person personDetails) {
        Person updated = personService.updatePerson(id, personDetails);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Integer id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

}
