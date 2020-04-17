package com.bookstore.controller;

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

import com.bookstore.model.Book;
import com.bookstore.response.BookResponse;
import com.bookstore.service.IBookService;

@RestController
@CrossOrigin
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
	public ResponseEntity<BookResponse> removeBook(@RequestParam("bookname") String bookName,@RequestHeader String token){
		return bookservice.removeBook(bookName,token);
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
