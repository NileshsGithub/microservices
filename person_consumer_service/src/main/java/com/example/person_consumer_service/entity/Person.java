package com.example.person_consumer_service.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String ipAddress;
    private Address address;

}
