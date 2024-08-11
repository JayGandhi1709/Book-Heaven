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
	List<String> genre;
	String isbn;
	Integer price;
	Integer page;
	Integer year;
	String language;
	String pdfUrl;

	
	public Book(
			String title,
			String desc,
			List<String> img,
			List<String> author,
			String publisher,
			String isbn,
			List<String> genre,
			Integer price,
			Integer page,
			Integer year,
			String language,
			String pdfUrl) {
		this.title = title;
		this.desc = desc;
		this.img = img;
		this.authors = author;
		this.genre = genre;
		this.publisher = publisher;
		this.price = price;
		this.page = page;
		this.isbn = isbn;
		this.year = year;
		this.language = language;
		this.pdfUrl = pdfUrl;
	}

	
}
