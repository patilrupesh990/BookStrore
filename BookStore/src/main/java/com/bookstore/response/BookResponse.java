package com.bookstore.response;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bookstore.model.Book;

import lombok.Data;

@Component
@Data
public class BookResponse {
	Book book;
	int statusCode;
	String response;
	List<Book> bookList;

	public BookResponse() {

	}


	public BookResponse(int statusCode, String response, List<Book> bookList) {
		super();
		this.statusCode = statusCode;
		this.response = response;
		this.bookList = bookList;
	}


	public BookResponse(Book book, int statusCode, String response) {
		super();
		this.book = book;
		this.statusCode = statusCode;
		this.response = response;
	}
	
	public BookResponse( int statusCode, String response) {
		super();
		this.statusCode = statusCode;
		this.response = response;
	}

}
