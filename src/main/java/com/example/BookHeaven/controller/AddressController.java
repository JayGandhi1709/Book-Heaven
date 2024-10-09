package com.example.BookHeaven.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.example.BookHeaven.model.Address;
import com.example.BookHeaven.service.AddressService;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> getAddressesByUserId(@PathVariable String userId) {
        try {
            List<Address> addresses = addressService.getAddressesByUserId(userId);
            return ResponseEntity.ok(JsonResponseUtils
                    .toJson(new ResponseMessage<List<Address>>(true, "Addresses retrieved successfully", addresses)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }

    @PostMapping
    public ResponseEntity<Object> addAddress(@RequestBody Address address) {
        try {
            Address createdAddress = addressService.addAddress(address);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(JsonResponseUtils
                            .toJson(new ResponseMessage<Address>(true, "Address added successfully", createdAddress)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAddress(@PathVariable String id) {
        try {
            addressService.deleteAddress(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(true, "Address deleted successfully")));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAddress(@PathVariable String id, @RequestBody Address updatedAddress) {
        try {
            Address address = addressService.updateAddress(id, updatedAddress);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(JsonResponseUtils
                            .toJson(new ResponseMessage<Address>(true, "Address updated successfully", address)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }
}
