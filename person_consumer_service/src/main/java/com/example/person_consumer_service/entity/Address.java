package com.example.person_consumer_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Long id;
    private String lane1;
    private String lane2;
    private String city;
    private String zipcode;
    private String country;
}
