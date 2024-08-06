package com.example.BookHeaven.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BookHeaven.repository.BookRepository;
import com.example.BookHeaven.repository.UserRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    // private CategoryRepository categoryRepository;

    AdminController(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/counts")
    public HashMap<String, Integer> getCounts() {
        HashMap<String, Integer> counts = new HashMap<>();
        counts.put("userCount", (int) userRepository.count());
        counts.put("bookCount", (int) bookRepository.count());
        // counts.put("productCount", productRepository.count());
        // counts.put("categoryCount", categoryRepository.count());
        // panding orders
        counts.put("pendingOrders", 10);
        counts.put("orderInProgress", 10);
        counts.put("oderSent", 01);
        counts.put("completeOrders", 201);
        counts.put("canceledOrders", 020);
        // completed orders
        return counts;
    }

    // @GetMapping("/countProducts")
    // public long countProducts() {
    // return productRepository.count();
    // }

    // @GetMapping("/countCategories")
    // public long countCategories() {
    // return categoryRepository.count();
    // }
}
