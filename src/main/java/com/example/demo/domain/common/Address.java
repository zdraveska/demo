package com.example.demo.domain.common;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Address {

    @Id
    String id;
    String city;
    String address;
    Double latitude;
    Double longitude;
    String mapUrl;
}
