package com.example.BookHeaven.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.BookHeaven.model.CarouselItem;

@Repository
public interface CarouselItemRepository extends MongoRepository<CarouselItem, String> {
    // Custom query methods can be added here if needed, e.g., findByTitle, etc.
}
