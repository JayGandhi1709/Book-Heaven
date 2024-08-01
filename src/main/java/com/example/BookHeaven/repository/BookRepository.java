package com.example.BookHeaven.repository;

import com.example.BookHeaven.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    // You can define custom query methods here if needed
}
