package com.bookstore.service;

import org.springframework.http.ResponseEntity;

import com.bookstore.model.Book;
import com.bookstore.response.BookResponse;

public interface IBookService {
	
	public ResponseEntity<BookResponse> addBook(Book book,String token);
	
	public ResponseEntity<BookResponse> removeBook(String bookName,String token);
	
	public ResponseEntity<BookResponse> getAllBooks(String token);
	
	public ResponseEntity<BookResponse> updateBookDetails(String bookName,Book updatedBook,String token);
}
