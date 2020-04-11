package com.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookExceptionController {
	@ExceptionHandler(value = BookNotFoundException.class)
	public ResponseEntity<Object> bookNotFoundExcpetion(BookNotFoundException bookNoteFoundExcpetion){
		return new ResponseEntity<>("Book Note Found",HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value =BookAlreadyExist.class)
	public ResponseEntity<Object> bookAlradyExistException(BookAlreadyExist bookAlreadyExist){
		return new ResponseEntity<>("Book Already Exist",HttpStatus.ALREADY_REPORTED);
	}
	@ExceptionHandler (value = InternalServerError.class)
	public ResponseEntity<Object> internalServerError(InternalServerError serverError){
		return new ResponseEntity<> ("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
