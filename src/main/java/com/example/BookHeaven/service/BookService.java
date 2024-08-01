package com.example.BookHeaven.service;

import com.example.BookHeaven.model.Book;
import com.example.BookHeaven.model.User;
import com.example.BookHeaven.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Get all books
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    // Get book by ID
    public Optional<Book> findBookById(String id) {
        return bookRepository.findById(id);
    }

    // Create a new book
    public Book createBook(Book book) {
    	try {
            return bookRepository.save(book);
        } catch (Exception e) {
            // Handle duplicate email exception
            throw new RuntimeException(e.getMessage());
        }
    }

    // Update an existing book
    public Book updateBook(String id, Book book) {
        if (bookRepository.existsById(id)) {
            book.setId(id);
            return bookRepository.save(book);
        } else {
            return null; // Or throw an exception
        }
    }

    // Delete a book
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }
}
