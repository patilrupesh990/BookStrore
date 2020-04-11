package com.bookstore.service;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import com.bookstore.model.Book;

public interface IBookService {
	
	public ResponseEntity<String> addBook(Book book);
	
	public RequestEntity<Object> removeBook();
	
	public RequestEntity<Object> getAllBooks();
	
}
