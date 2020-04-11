package com.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookstore.dao.IBookDAO;
import com.bookstore.model.Book;
import com.bookstore.response.BookAlreadyExist;

@Service
public class BookServiceImpl implements IBookService{

	@Autowired
	IBookDAO bookdao;
	@Override
	public ResponseEntity<String> addBook(Book book) {
		String bookName=book.getBookName().toLowerCase().trim();
		if(bookdao.getBookByName(bookName)==null) {
			bookdao.addBook(book);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new String("added book"));
		}else {
			throw new BookAlreadyExist();
		}
	}

	@Override
	public RequestEntity<Object> removeBook() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestEntity<Object> getAllBooks() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
