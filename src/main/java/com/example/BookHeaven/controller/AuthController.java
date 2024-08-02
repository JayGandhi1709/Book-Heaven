package com.example.BookHeaven.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BookHeaven.Utils.JsonResponseUtils;
import com.example.BookHeaven.Utils.JwtUtil;
import com.example.BookHeaven.Utils.ResponseMessage;
import com.example.BookHeaven.model.User;
import com.example.BookHeaven.service.UserDetailsServiceImpl;
import com.example.BookHeaven.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public AuthController(PasswordEncoder passwordencoder, UserService userService,
            AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil) {
        this.passwordEncoder = passwordencoder;
        this.userService = userService;

    }

    @GetMapping("/health-check")
    public String hello() {
        return "ok";
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody User user) throws Exception {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            User createdUser = userService.createUser(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(JsonResponseUtils
                    .toJson(new ResponseMessage<User>(true, "User created successfully", createdUser)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }

    // write a code for login Api and return it success ,msg and token
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
        try {
            String token = userService.authenticate(user.getEmail(), user.getPassword());

            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<>(true, "Login successful", token)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }

    // @PostMapping("/forgot-password")
    // public ResponseEntity<Object> forgotPassword(@RequestBody User user) {
    // try {
    // userService.forgotPassword(user.getEmail());
    // return ResponseEntity.status(HttpStatus.ACCEPTED)
    // .body(JsonResponseUtils.toJson(new ResponseMessage<>(true, "Password reset
    // link sent to email")));
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false,
    // e.getMessage())));
    // }
    // }

    // @PutMapping("/reset-password")
    // public ResponseEntity<Object> resetPassword(@RequestBody User user) {
    // try {
    // userService.resetPassword(user.getEmail(), user.getPassword());
    // return ResponseEntity.status(HttpStatus.ACCEPTED)
    // .body(JsonResponseUtils.toJson(new ResponseMessage<>(true, "Password reset
    // successfully")));
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false,
    // e.getMessage())));
    // }
    // }

}
