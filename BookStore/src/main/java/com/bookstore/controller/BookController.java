package com.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<BookResponse> addBook(@RequestBody Book book){
		return bookservice.addBook(book);
	}
	
	@PutMapping("/update")
	public ResponseEntity<BookResponse> updateBook(@RequestBody Book book,@RequestParam("bookname") String bookName){
		return bookservice.updateBookDetails(bookName, book);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<BookResponse> removeBook(@RequestParam("bookname") String bookName){
		return bookservice.removeBook(bookName);
	}
	
	@GetMapping("/getAllBooks")
	public ResponseEntity<BookResponse> getBooks(){
		return bookservice.getAllBooks();
	}
	
}
