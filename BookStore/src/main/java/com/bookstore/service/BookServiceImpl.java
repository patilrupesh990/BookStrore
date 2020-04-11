package com.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookstore.dao.IBookDAO;
import com.bookstore.exception.BookAlreadyExist;
import com.bookstore.exception.BookNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.response.BookResponse;

@Service
public class BookServiceImpl implements IBookService {

	@Autowired
	IBookDAO bookdao;

	@Override
	public ResponseEntity<BookResponse> addBook(Book book) {
		String bookName = book.getBookName().toLowerCase().trim();
		if (bookdao.getBookByName(bookName) == null) {
			bookdao.addBook(book);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new BookResponse(book, 202, book.getBookName() + " Book Added"));
		} else {
			throw new BookAlreadyExist();
		}
	}

	@Override
	public ResponseEntity<BookResponse> removeBook(String bookName) {
		if (bookdao.getBookByName(bookName) != null) {
			if (bookdao.deleteBook(bookName) > 0)
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new BookResponse(202, bookName + " Deleted Successfully"));
			else
				return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
						.body(new BookResponse(502, bookName + " Could Not delete Please Try Again"));
		} else {
			throw new BookNotFoundException();
		}

	}

	@Override
	public ResponseEntity<BookResponse> getAllBooks() {
		if (bookdao.getAllBooks() != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(
					new BookResponse(202, "Total Books Are:" + bookdao.getAllBooks().size(), bookdao.getAllBooks()));
		} else {
			throw new BookNotFoundException();
		}
	}

	@Override
	public ResponseEntity<BookResponse> updateBookDetails(String bookName, Book updatedBook) {
		if (bookdao.getBookByName(updatedBook.getBookName()) != null) {
			bookdao.updateBook(updatedBook, bookName);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new BookResponse(updatedBook, 202, bookName + " Updated"));
		} else {
			throw new BookNotFoundException();
		}
	}

}
