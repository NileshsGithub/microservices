package com.example.person_consumer_service.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private Integer id;
    private String first_name;
    private String last_name;
    private String email;
    private String gender;
    private String ip_address;
    private Address address;

}
