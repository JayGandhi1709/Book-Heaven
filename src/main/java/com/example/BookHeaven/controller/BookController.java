package com.example.BookHeaven.controller;

import java.io.IOException;
import java.util.List;

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
import com.example.BookHeaven.model.Book;
import com.example.BookHeaven.service.BookService;
import com.example.BookHeaven.service.CloudinaryService;

@RestController
@RequestMapping("/api")
public class BookController {

	private final BookService bookService;

	private final CloudinaryService cloudinaryService;

	BookController(BookService bookService, CloudinaryService cloudinaryService) {
		this.bookService = bookService;
		this.cloudinaryService = cloudinaryService;
	}

	// @Autowired
	// private FileService fileService;

	// Get all books
	@GetMapping({ "/books", "/admin/books" })
	// public List<Book> getAllBooks() {
	// return bookService.findAllBooks();
	// }

	// @GetMapping
	public ResponseEntity<Object> getAllBooks() throws Exception {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(JsonResponseUtils
					.toJson(new ResponseMessage<List<Book>>(true, "All Books fetched successfully",
							bookService.getAllBooks())));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
		}
	}

	// Get a book by ID
	// public ResponseEntity<Book> getBookById(@PathVariable String id) {
	// Optional<Book> book = bookService.findBookById(id);
	// return book.map(ResponseEntity::ok).orElseGet(() ->
	// ResponseEntity.notFound().build());
	// }

	@GetMapping("/admin/books/{id}")
	public ResponseEntity<Object> getBookById(@PathVariable String id) throws Exception {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(JsonResponseUtils.toJson(new ResponseMessage<Book>(true, "All Books fetched successfully",
							bookService.getBookById(id))));
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
		}
	}

	@GetMapping("/hello")
	public String hello() {
		return "Hello";
	}

	@PostMapping("/admin/books")
	public ResponseEntity<Object> createBook(
			@RequestParam String title,
			@RequestParam String desc,
			@RequestParam List<String> authors,
			@RequestParam String publisher,
			@RequestParam List<String> genre,
			@RequestParam List<MultipartFile> img,
			@RequestParam String isbn,
			@RequestParam Integer publicationYear,
			@RequestParam String page,
			@RequestParam Integer physicalPrice,
			@RequestParam Integer digitalPrice,
			@RequestParam Boolean hasPhysicalCopy,
			@RequestParam Boolean hasDigitalCopy,
			@RequestParam String language,
			@RequestParam(required = false) MultipartFile pdf) throws IOException {

		try {

			// Upload images to cloud service and get URLs
			List<String> imgData = this.cloudinaryService.uploadMultipleImages(img);

			// Upload PDF if a digital copy is available
			String pdfData = null;
			if (hasDigitalCopy && pdf != null) {
				pdfData = this.cloudinaryService.uploadPDF(pdf);
			}

			// Create the Book object
			Book book = new Book(
					title,
					desc,
					imgData,
					authors,
					publisher,
					isbn,
					genre,
					physicalPrice,
					digitalPrice,
					page,
					publicationYear,
					language,
					pdfData,
					hasPhysicalCopy,
					hasDigitalCopy);

			// Save the book using the service
			Book createdBook = bookService.createBook(book);

			// Return a success response
			return ResponseEntity.status(HttpStatus.CREATED).body(
					JsonResponseUtils
							.toJson(new ResponseMessage<Book>(true, "Book created successfully", createdBook)));

		} catch (RuntimeException e) {
			// Handle runtime exceptions
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
		} catch (Exception e) {
			// Handle other exceptions
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
		}
	}

	// @PostMapping
	// public ResponseEntity<Object> createBook(@RequestBody Book book) throws
	// IOException {
	// try {
	// Book createdBook = bookService.createBook(book);
	// return
	// ResponseEntity.status(HttpStatus.CREATED).body(JsonResponseUtils.toJson(new
	// ResponseMessage<Book>(true, "Book created successfully",createdBook)));
	// } catch (RuntimeException e) {
	// return
	// ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JsonResponseUtils.toJson(new
	// ResponseMessage<Object>(false, e.getMessage())));
	// }catch(Exception e) {
	// return
	// ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JsonResponseUtils.toJson(new
	// ResponseMessage<Object>(false, e.getMessage())));
	// }
	// }

	// Update an existing book
	// public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody
	// Book book) {
	// Book updatedBook = bookService.updateBook(id, book);
	// if (updatedBook != null) {
	// return ResponseEntity.ok(updatedBook);
	// } else {
	// return ResponseEntity.notFound().build();
	// }
	// }

	@PutMapping("/admin/books/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable String id, @RequestBody Book book) {
		try {
			Book updatedBook = bookService.updateBook(id, book);

			if (updatedBook != null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(JsonResponseUtils
								.toJson(new ResponseMessage<Book>(true, "Book updated successfully", updatedBook)));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, "User not found")));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
		}
	}

	// Delete a book by ID
	// public ResponseEntity<Void> deleteBook(@PathVariable String id) {
	// Book book = bookService.getBookById(id);
	// if (book != null) {
	// bookService.deleteBook(id);
	// return ResponseEntity.noContent().build();
	// } else {
	// return ResponseEntity.notFound().build();
	// }
	// }

	@DeleteMapping("/admin/books/{id}")
	public ResponseEntity<Object> deleteBook(@PathVariable String id) {
		try {
			Book deletedBook = bookService.deleteBook(id);

			if (deletedBook != null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(JsonResponseUtils
								.toJson(new ResponseMessage<Book>(true, "Book deleted successfully", deletedBook)));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, "User not found")));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(JsonResponseUtils.toJson(new ResponseMessage<Object>(false, e.getMessage())));
		}
	}
}
