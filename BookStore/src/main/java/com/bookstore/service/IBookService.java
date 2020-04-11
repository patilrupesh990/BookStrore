package com.bookstore.service;

import org.springframework.http.ResponseEntity;

import com.bookstore.model.Book;
import com.bookstore.response.BookResponse;

public interface IBookService {
	
	public ResponseEntity<BookResponse> addBook(Book book);
	
	public ResponseEntity<BookResponse> removeBook(String bookName);
	
	public ResponseEntity<BookResponse> getAllBooks();
	
	public ResponseEntity<BookResponse> updateBookDetails(String bookName,Book updatedBook);
}
