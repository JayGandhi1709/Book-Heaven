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
    Integer publicationYear;
    String pdfUrl;
    
 // Default constructor
//    public Book() {}

    // Parameterized constructor
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
    		Integer publicationYear, 
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
        this.publicationYear = publicationYear;
        this.pdfUrl = pdfUrl;
    }

    // 	Getters and Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<String> getImg() {
		return img;
	}

	public void setImg(List<String> img) {
		this.img = img;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public List<String> getGenre() {
		return genre;
	}

	public void setGenre(List<String> genre) {
		this.genre = genre;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(Integer publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getPdfUrl() {
		return pdfUrl;
	}

	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}
    
  
}
