package com.example.person_consumer_service.service;

import com.example.person_consumer_service.client.PersonClient;
import com.example.person_consumer_service.entity.Person;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PersonService {

    private final PersonClient personClient;

    public PersonService(PersonClient personClient){
        this.personClient = personClient;
    }

    public List<Person> getAllPersons(){
        return personClient.getAllPersons();
    }

    public Person getPersonById(Integer id){
        return personClient.getPersonById(id);
    }
}
