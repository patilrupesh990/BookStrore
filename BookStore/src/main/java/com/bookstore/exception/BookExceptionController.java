package com.bookstore.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.bookstore.response.CustomErrorResponse;

@ControllerAdvice
public class BookExceptionController {
	@ExceptionHandler(value = BookNotFoundException.class)
	public ResponseEntity<Object> bookNotFoundExcpetion(BookNotFoundException bookNoteFoundExcpetion) {
		return new ResponseEntity<>("Book Note Found", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = BookAlreadyExist.class)
	public ResponseEntity<Object> bookAlradyExistException(BookAlreadyExist bookAlreadyExist) {
		return new ResponseEntity<>("Book Already Exist", HttpStatus.ALREADY_REPORTED);
	}

	@ExceptionHandler(value = InternalServerError.class)
	public ResponseEntity<Object> internalServerError(InternalServerError serverError) {
		return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = UserDoesNotExistException.class)
	public ResponseEntity<Object> userDoesNotExistException(Exception ex, WebRequest request) {
		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ex.getMessage());
		errors.setStatus(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(InvalidTokenOrExpiredException.class)
	public ResponseEntity<Object> invalidTokenOrExpiredException(Exception ex, WebRequest request) {
		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ex.getMessage());
		errors.setStatus(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}
}
