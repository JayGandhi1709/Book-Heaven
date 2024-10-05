package com.example.BookHeaven.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "orderItems")
public class OrderItem {
    private Book book; // Book details
    private int quantity; // Quantity of the book in the order
    private String bookType; // "physical" or "digital"
}
