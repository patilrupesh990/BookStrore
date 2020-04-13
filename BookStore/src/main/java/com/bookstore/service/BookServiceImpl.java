package com.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.bookstore.dao.IBookDAO;
import com.bookstore.exception.BookAlreadyExist;
import com.bookstore.exception.BookNotFoundException;
import com.bookstore.exception.InvalidTokenOrExpiredException;
import com.bookstore.exception.UserDoesNotExistException;
import com.bookstore.model.Book;
import com.bookstore.model.UserData;
import com.bookstore.response.BookResponse;
import com.bookstore.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookServiceImpl implements IBookService {

	@Autowired
	IBookDAO bookdao;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	private JwtTokenUtil generateToken;

	@Override
	public ResponseEntity<BookResponse> addBook(Book book, String token) {
		if (verifyUser(token)) {
			String bookName = book.getBookName().toLowerCase().trim();
			if (bookdao.getBookByName(bookName) == null) {
				bookdao.addBook(book);
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new BookResponse(book, 202, book.getBookName() + " Book Added"));
			} else {
				throw new BookAlreadyExist();
			}
		} else {
			throw new UserDoesNotExistException("User Does Not Exist", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<BookResponse> removeBook(String bookName, String token) {
		if (verifyUser(token)) {
			log.info("book data"+bookdao.getBookByName(bookName));
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
		} else {
			throw new UserDoesNotExistException("User Does Not Exist", HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public ResponseEntity<BookResponse> getAllBooks(String token) {
		if (verifyUser(token)) {
			if (bookdao.getAllBooks() != null) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(new BookResponse(202,
						"Total Books Are:" + bookdao.getAllBooks().size(), bookdao.getAllBooks()));
			} else {
				throw new BookNotFoundException();
			}
		} else {
			throw new UserDoesNotExistException("User Does Not Exist", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<BookResponse> updateBookDetails(String bookName, Book updatedBook, String token) {
		if (verifyUser(token)) {
			if (bookdao.getBookByName(updatedBook.getBookName()) != null) {
				bookdao.updateBook(updatedBook, bookName);
				return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new BookResponse(updatedBook, 202, bookName + " Updated"));
			} else {
				throw new BookNotFoundException();
			}
		} else {
			throw new UserDoesNotExistException("User Does Not Exist", HttpStatus.BAD_REQUEST);
		}
	}

	public boolean verifyUser(String token) {
		log.info("-------->>>>>>>>>>>>>Calling USerApi From NotesApi<<<<<<<<<<<<<<<<--------------------");
		UserData userData = restTemplate.getForObject("http://localhost:8092/users/" + token, UserData.class);
		log.info("--------->>>>>>>>>>>>Accessing DataFrom UserApi<<<<<<<<<<<---------------------");
		try {
		log.info("verifyUserApi Using RestTemplate From UserApi Success--------->:"
				+ (userData.getUId() == generateToken.parseToken(token)));
		return (userData.getUId() == generateToken.parseToken(token));
		}catch (SignatureVerificationException|JWTDecodeException|AlgorithmMismatchException e) {
			throw new InvalidTokenOrExpiredException("Invalid Token or Token Expired", HttpStatus.BAD_REQUEST);
		}
	}

}
