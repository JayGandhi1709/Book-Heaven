package com.example.BookHeaven.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BookHeaven.Utils.JsonResponseUtils;
import com.example.BookHeaven.Utils.ResponseMessage;
import com.example.BookHeaven.model.User;
import com.example.BookHeaven.repository.BookRepository;
import com.example.BookHeaven.repository.UserRepository;
import com.example.BookHeaven.repository.CarouselItemRepository;

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

    @GetMapping("/health-check")
    public String hello() {
        return "Admin API ok";
    }

    @GetMapping("/counts")
    public ResponseEntity<Object> getCounts() {
        try {
            HashMap<String, Integer> counts = new HashMap<>();
            counts.put("userCount", (int) userRepository.count());
            counts.put("bookCount", (int) bookRepository.count());
            counts.put("carouselCount", (int) CarouselItemRepository.count());
            // counts.put("categoryCount", categoryRepository.count());
            // panding orders
            counts.put("totalOrders", 50);
            counts.put("pendingOrders", 10);
            counts.put("orderInProgress", 10);
            counts.put("orderSent", 0);
            counts.put("completeOrders", 20);
            counts.put("canceledOrders", 20);
            // completed orders
            // return counts;
            return ResponseEntity.status(HttpStatus.CREATED).body(JsonResponseUtils
                    .toJson(new ResponseMessage<>(true, "counts fetched!", counts)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
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
