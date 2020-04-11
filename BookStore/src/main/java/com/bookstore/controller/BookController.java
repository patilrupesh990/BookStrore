package com.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.Book;
import com.bookstore.service.IBookService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/books")
@Slf4j
@CrossOrigin
public class BookController {
	
	@Autowired
	IBookService bookservice;
	@PostMapping("/add")
	public ResponseEntity<String> addBook(@RequestBody Book book){
		log.info("controller");
		return bookservice.addBook(book);
		
	}
}
