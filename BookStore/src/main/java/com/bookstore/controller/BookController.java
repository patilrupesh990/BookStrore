package com.bookstore.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.model.Book;
import com.bookstore.response.BookResponse;
import com.bookstore.service.IBookService;

@RestController
@CrossOrigin("*")
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	IBookService bookservice;
	
	@PostMapping("/add")
	public ResponseEntity<BookResponse> addBook(@RequestBody Book book,@RequestHeader String token){
		return bookservice.addBook(book,token);
	}
	
	@PutMapping("/update")
	public ResponseEntity<BookResponse> updateBook(@RequestBody Book book,@RequestParam("bookname") String bookName,@RequestHeader String token){
		return bookservice.updateBookDetails(bookName, book,token);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<BookResponse> removeBook(@RequestParam("bookId") int bookId,@RequestHeader String token){
		return bookservice.removeBook(bookId,token);
	}
	
	@PostMapping("/upload")
	public ResponseEntity<BookResponse> uploadImage(@RequestParam("imageFile") MultipartFile file,@RequestParam("bookId") int bookId,@RequestHeader String token) throws IOException {
		return bookservice.uploadBookImage(token, file.getBytes(), bookId);
	}
	
	@GetMapping("/getAllBooks")
	public ResponseEntity<BookResponse> getAllBooks(@RequestHeader String token){
		return bookservice.getAllBooks(token);
	}
	@GetMapping("/sellerBooks")
	public ResponseEntity<BookResponse> getSellerBooks(@RequestHeader String token){
		return bookservice.getSellerBooks(token);
	}
}
