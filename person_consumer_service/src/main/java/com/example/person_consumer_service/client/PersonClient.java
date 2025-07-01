//package com.example.person_consumer_service.client;
//
//import com.example.person_consumer_service.entity.Person;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import java.util.List;
//
//@FeignClient(name = "csv-reader", url = "http://localhost:8082")
//public interface PersonClient {
//    @GetMapping("/api/persons")
//    List<Person> getAllPersons();
//
//    @GetMapping("/api/persons/{id}")
//    Person getPersonById(@PathVariable("id")Integer id);
//}
