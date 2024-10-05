package com.example.BookHeaven.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BookHeaven.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId); // Method to find orders by user ID
}
