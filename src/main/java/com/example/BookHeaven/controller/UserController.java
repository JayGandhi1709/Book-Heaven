package com.example.BookHeaven.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
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
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/hello")
    public String hello() {
    	return "Hello";
    }

//    @PostMapping
//    public User createUser(@RequestBody User user) {
//    	return  userService.createUser(user);
//    }
    
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody User user) throws Exception {
    	try {
//    		user.setPassword(passwordEncoder.encode(user.getPassword()));
    		System.out.println("User");
    		User createdUser = userService.createUser(user);
    		return ResponseEntity.status(HttpStatus.CREATED).body(JsonResponseUtils.toJson(new ResponseMessage<User>(true, "User created successfully",createdUser)));
//    		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    	} catch (RuntimeException e) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, "e.getMessage()1")));
    	}catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, "e.getMessage()2")));
    		
    	}
    }
    
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
//    	System.out.println("user : "+user.getPassword());
    	User existingUser = userService.getUserByEmail(user.getEmail());
//    	System.out.println("existing user : "+existingUser.getPassword());
    	
//    	System.out.println(user.getPassword().equals(existingUser.getPassword()));
    	
//    	if (existUser != null && passwordEncoder.matches(user.getPassword(), existUser.getPassword())) {
   		if (existingUser != null && user.getPassword().equals(existingUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(JsonResponseUtils.toJson(new ResponseMessage<>(true, "Login successful", existingUser)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JsonResponseUtils.toJson(new ResponseMessage<>(false, "Invalid email or password")));
        }
    	
    }
    

    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
