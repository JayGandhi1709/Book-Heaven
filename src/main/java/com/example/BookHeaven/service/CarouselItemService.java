package com.example.BookHeaven.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BookHeaven.model.CarouselItem;
import com.example.BookHeaven.repository.CarouselItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarouselItemService {

    @Autowired
    private CarouselItemRepository carouselItemRepository;

    // Get all carousel items count
    public long getAllItemsCount() {
        return carouselItemRepository.count();
    }

    public List<CarouselItem> getAllItems() {
        return carouselItemRepository.findAll();
    }

    public CarouselItem getItemById(String id) {
        return carouselItemRepository.findById(id).orElse(null);
    }

    public CarouselItem createCarouselItem(CarouselItem item) {
        return carouselItemRepository.save(item);
    }

    public CarouselItem updateCarouselItem(String id, CarouselItem updatedItem) {
        Optional<CarouselItem> existingItem = carouselItemRepository.findById(id);
        if (existingItem.isPresent()) {
            CarouselItem item = existingItem.get();
            item.setTitle(updatedItem.getTitle());
            item.setDescription(updatedItem.getDescription());
            item.setImageUrl(updatedItem.getImageUrl());
            item.setDisplayOrder(updatedItem.getDisplayOrder());
            return carouselItemRepository.save(item);
        } else {
            return null;
        }
    }

    public void deleteCarouselItem(String id) {
        carouselItemRepository.deleteById(id);
    }
}
