package com.example.BookHeaven.service;

import com.example.BookHeaven.model.Address;
import com.example.BookHeaven.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAddressesByUserId(String userId) {
        return addressRepository.findByUserId(userId);
    }

    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }

    public void deleteAddress(String id) {
        addressRepository.deleteById(id);
    }

    // update the address with the given id
    public Address updateAddress(String id, Address updatedAddress) {
        Address address = addressRepository.findById(id).orElse(null);
        if (address == null) {
            return null;
        }
        address.setStreet(updatedAddress.getStreet());
        address.setCity(updatedAddress.getCity());
        address.setState(updatedAddress.getState());
        address.setContactNumber(updatedAddress.getContactNumber());
        address.setZipCode(updatedAddress.getZipCode());
        return addressRepository.save(address);
    }

}
