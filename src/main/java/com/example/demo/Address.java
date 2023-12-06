package com.example.demo;

import lombok.Data;

@Data
public class Address {
    private String country;
    private String city;
    private String zipCode;

    public Address(String country, String city, String zipCode) {
        this.country = country;
        this.city = city;
        this.zipCode = zipCode;
    }
}
