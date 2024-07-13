package com.example.BookHeaven.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BookHeaven.model.User;

public interface UserRepository extends MongoRepository<User,String>{
	User findByEmail(String email);
}
