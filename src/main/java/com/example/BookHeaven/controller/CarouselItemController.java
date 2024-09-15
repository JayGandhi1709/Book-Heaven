package com.example.BookHeaven.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.BookHeaven.Utils.JsonResponseUtils;
import com.example.BookHeaven.Utils.ResponseMessage;
import com.example.BookHeaven.model.CarouselItem;
import com.example.BookHeaven.service.CarouselItemService;
import com.example.BookHeaven.service.CloudinaryService;

@RestController
@RequestMapping("/api")
public class CarouselItemController {

    private final CarouselItemService carouselItemService;

    private final CloudinaryService cloudinaryService;

    CarouselItemController(CarouselItemService carouselItemService, CloudinaryService cloudinaryService) {
        this.carouselItemService = carouselItemService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("carousel/health-check")
    public String hello() {
        return "Carousel Ok";
    }

    // Get all carousel items count
    @GetMapping("admin/carousel/count")
    public ResponseEntity<Object> getAllItemsCount() {
        try {
            int count = carouselItemService.getAllItemsCount();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(JsonResponseUtils.toJson(
                            new ResponseMessage<>(true, "Carousel Count Fatched successfully", count)));
        } catch (RuntimeException e) {
            // Handle runtime exceptions
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }

    // Get all carousel items
    @GetMapping("/carousel")
    public ResponseEntity<Object> getAllItems() {
        try {
            List<CarouselItem> items = carouselItemService.getAllItems();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(JsonResponseUtils.toJson(
                            new ResponseMessage<List<CarouselItem>>(true, "Carousel Fatched successfully", items)));
        } catch (RuntimeException e) {
            // Handle runtime exceptions
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }

    // Get a carousel item by ID
    @GetMapping("admin/carousel/{id}")
    public ResponseEntity<Object> getItemById(@PathVariable String id) {
        try {
            CarouselItem item = carouselItemService.getItemById(id);
            if (item == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, "Carousel not found")));
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(JsonResponseUtils.toJson(
                            new ResponseMessage<CarouselItem>(true, "Carousel Fatched successfully", item)));
        } catch (RuntimeException e) {
            // Handle runtime exceptions
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }

    // Create a new carousel item
    @PostMapping("admin/carousel")
    public ResponseEntity<Object> createCarouselItem(@RequestParam String title,
            @RequestParam String description, @RequestParam MultipartFile image) {
        try {

            if (image == null || image.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, "File is missing or empty")));
            }

            String imageUrl = this.cloudinaryService.uploadImage(image, "carousel");
            int displayOrder = carouselItemService.getAllItemsCount() + 1;
            CarouselItem item = new CarouselItem(title, description, imageUrl, displayOrder);
            CarouselItem newItem = carouselItemService.createCarouselItem(item);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(JsonResponseUtils.toJson(
                            new ResponseMessage<CarouselItem>(true, "Carousel created successfully", newItem)));
        } catch (RuntimeException e) {
            // Handle runtime exceptions
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }

    // // Update an existing carousel item
    @PutMapping("admin/carousel/{id}")
    public ResponseEntity<Object> updateCarouselItem(@PathVariable String id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description) {

        try {
            CarouselItem newItem = carouselItemService.updateCarouselItem(id,
                    new CarouselItem(title, description));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(JsonResponseUtils.toJson(
                            new ResponseMessage<CarouselItem>(true, "Carousel updated successfully", newItem)));
        } catch (RuntimeException e) {
            // Handle runtime exceptions
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }

    // Delete a carousel item
    @DeleteMapping("admin/carousel/{id}")
    public ResponseEntity<Object> deleteCarouselItem(@PathVariable String id) {
        try {
            var deletedItem = carouselItemService.deleteCarouselItem(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(JsonResponseUtils
                            .toJson(new ResponseMessage<Object>(true, "Carousel deleted successfully", deletedItem)));
        } catch (RuntimeException e) {
            // Handle runtime exceptions
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }

    // write a method to update the display order of the carousel items by id of
    // list of display order
    @PutMapping("admin/carousel/update-display-order")
    public ResponseEntity<Object> updateDisplayOrder(@RequestBody List<CarouselItem> items) {
        try {
            for (CarouselItem item : items) {
                carouselItemService.updateDisplayOrder(item.getId(), item.getDisplayOrder());
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(JsonResponseUtils
                            .toJson(new ResponseMessage<Object>(true, "Display order updated successfully")));
        } catch (RuntimeException e) {
            // Handle runtime exceptions
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
        }
    }

}
