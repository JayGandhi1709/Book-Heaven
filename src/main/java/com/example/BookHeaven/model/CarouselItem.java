package com.example.BookHeaven.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "carouselItems")
public class CarouselItem {

    @Id
    private String id;

    private String title;
    private String description;
    private String imageUrl;
    private int displayOrder; // To control the order of carousel items

    public CarouselItem(String title, String description, String imageUrl, int displayOrder) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.displayOrder = displayOrder;
    }

    public CarouselItem(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
