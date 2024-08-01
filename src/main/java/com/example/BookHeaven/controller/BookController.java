package com.example.BookHeaven.controller;

import com.example.BookHeaven.Utils.JsonResponseUtils;
import com.example.BookHeaven.Utils.ResponseMessage;
import com.example.BookHeaven.model.Book;
import com.example.BookHeaven.model.User;
import com.example.BookHeaven.service.BookService;
import com.example.BookHeaven.service.CloudinaryService;
import com.example.BookHeaven.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookService bookService;
	@Autowired
	private CloudinaryService cloudinaryService;
//    @Autowired
//    private FileService fileService;

	// Get all books
	@GetMapping
	public List<Book> getAllBooks() {
		return bookService.findAllBooks();
	}

	// Get a book by ID
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable String id) {
		Optional<Book> book = bookService.findBookById(id);
		return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/hello")
	public String hello() {
		return "Hello";
	}

	// Create a new book
	@PostMapping
	public ResponseEntity<Object> createBook(@RequestParam("title") String title, @RequestParam("desc") String desc,
			@RequestParam("authors") List<String> authors, @RequestParam("publisher") String publisher, @RequestParam("genre") List<String> genre, @RequestParam("img") List<MultipartFile> img,
			@RequestParam("isbn") String isbn, @RequestParam("publicationYear") Integer publicationYear, @RequestParam("page") Integer pageCount, @RequestParam("price") Integer price,
			@RequestParam("PdfFile") MultipartFile pdf) throws IOException {
		try {
			List<String> imgData = this.cloudinaryService.uploadMultipleImages(img);
			
//			int pageCount = getPageCount(pdf.getInputStream());

			String pdfData = this.cloudinaryService.uploadPDF(pdf);

			Book book = new Book(
		            title,
		            desc,
		            imgData,
		            authors,
		            publisher,
		            isbn,
		            genre,
		            price,
		            pageCount,
		            publicationYear,
		            pdfData
		        );

			Book createdBook = bookService.createBook(book);

			return ResponseEntity.status(HttpStatus.CREATED).body(
					JsonResponseUtils.toJson(new ResponseMessage<Book>(true, "Book created successfully", createdBook)));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
		}
	}

//    @PostMapping
//    public ResponseEntity<Object> createBook(@RequestBody Book book) throws IOException {
//    	try {
//    		Book createdBook = bookService.createBook(book);
//    		return ResponseEntity.status(HttpStatus.CREATED).body(JsonResponseUtils.toJson(new ResponseMessage<Book>(true, "Book created successfully",createdBook)));
//    	} catch (RuntimeException e) {
//    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
//    	}catch(Exception e) {
//    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));	
//    	}
//    }

	// Update an existing book
	@PutMapping("/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody Book book) {
		Book updatedBook = bookService.updateBook(id, book);
		if (updatedBook != null) {
			return ResponseEntity.ok(updatedBook);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Delete a book by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable String id) {
		Optional<Book> book = bookService.findBookById(id);
		if (book.isPresent()) {
			bookService.deleteBook(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
