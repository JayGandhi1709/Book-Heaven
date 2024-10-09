package com.example.BookHeaven.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "addresses")
public class Address {

    @Id
    private String id;
    private String userId; // Reference to the user
    private String street;
    private String city;
    private String state;
    private String contactNumber;
    private String zipCode;

}
