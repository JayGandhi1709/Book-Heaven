package com.example.BookHeaven.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.example.BookHeaven.Utils.ResponseMessage;
import com.example.BookHeaven.model.User;
import com.example.BookHeaven.service.UserService;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public UserController(PasswordEncoder passwordencoder, UserService userService) {
        this.passwordEncoder = passwordencoder;
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        try {
            return userService.getAllUsers();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        try {
            return userService.getUserById(id);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/health-check")
    public String healthCheck() {
        return "ok";
    }

    @PostMapping
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

    // // write a code for login Api and return it success ,msg and token
    // @PostMapping("/login")
    // public ResponseEntity<Object> loginUser(@RequestBody User user) {
    // try {
    // String token = userService.authenticate(user.getEmail(), user.getPassword());

    // return ResponseEntity.status(HttpStatus.ACCEPTED)
    // .body(JsonResponseUtils.toJson(new ResponseMessage<>(true, "Login
    // successful", token)));
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false,
    // e.getMessage())));
    // }
    // }

    // @PostMapping("/login")
    // public ResponseEntity<Object> loginUser(@RequestBody User user) {
    // try {

    // authenticationManager
    // .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),
    // user.getPassword()));

    // UserDetails userDetails =
    // userDetailsService.loadUserByUsername(user.getEmail());

    // String token = jwtUtil.generateToken(userDetails.getUsername());

    // return ResponseEntity.status(HttpStatus.ACCEPTED)
    // .body(JsonResponseUtils
    // .toJson(new ResponseMessage<>(true, "Login successful", token)));

    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false,
    // e.getMessage())));
    // }

    // }

    // @PostMapping("/login")
    // public ResponseEntity<Object> loginUser(@RequestBody User user) {
    // try {

    // User existingUser = userService.getUserByEmail(user.getEmail());

    // if (existingUser != null && passwordEncoder.matches(user.getPassword(),
    // existingUser.getPassword())) {
    // return ResponseEntity.status(HttpStatus.ACCEPTED)
    // .body(JsonResponseUtils
    // .toJson(new ResponseMessage<User>(true, "Login successful", existingUser)));
    // } else {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body(JsonResponseUtils
    // .toJson(new ResponseMessage<Object>(false, "Invalid email or password")));
    // }
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false,
    // e.getMessage())));
    // }

    // }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable String id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);

            if (updatedUser != null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(JsonResponseUtils
                                .toJson(new ResponseMessage<User>(true, "User updated successfully", updatedUser)));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, "User not found")));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable String id) {
        try {
            User deletedUser = userService.deleteUser(id);

            if (deletedUser != null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(JsonResponseUtils
                                .toJson(new ResponseMessage<User>(true, "User deleted successfully", deletedUser)));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, "User not found")));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }
}
