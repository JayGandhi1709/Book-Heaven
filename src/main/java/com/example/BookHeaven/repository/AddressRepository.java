package com.example.BookHeaven.repository;

import com.example.BookHeaven.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface AddressRepository extends MongoRepository<Address, String> {
    List<Address> findByUserId(String userId);
}
