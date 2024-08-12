package com.example.BookHeaven.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "books")
public class Book {
    @Id
    String id;
    String title;
    String desc;
    List<String> img;
    List<String> authors;
    String publisher;
    Integer publicationYear;
    List<String> genre;
    String isbn;
    Integer physicalPrice;
    Integer digitalPrice;
    String page;
    String language;
    Boolean hasPhysicalCopy;
    Boolean hasDigitalCopy;
    String pdfUrl;

    public Book(
            String title,
            String desc,
            List<String> img,
            List<String> authors,
            String publisher,
            String isbn,
            List<String> genre,
            Integer physicalPrice,
            Integer digitalPrice,
            String page,
            Integer publicationYear,
            String language,
            String pdfUrl,
            Boolean hasPhysicalCopy,
            Boolean hasDigitalCopy) {
        this.title = title;
        this.desc = desc;
        this.img = img;
        this.authors = authors;
        this.genre = genre;
        this.publisher = publisher;
        this.isbn = isbn;
        this.physicalPrice = physicalPrice;
        this.digitalPrice = digitalPrice;
        this.page = page;
        this.publicationYear = publicationYear;
        this.language = language;
        this.pdfUrl = pdfUrl;
        this.hasPhysicalCopy = hasPhysicalCopy;
        this.hasDigitalCopy = hasDigitalCopy;
    }
}
