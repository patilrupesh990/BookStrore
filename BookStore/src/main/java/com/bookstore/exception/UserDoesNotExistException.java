package com.bookstore.exception;

import org.springframework.http.HttpStatus;

public class UserDoesNotExistException extends RuntimeException{
	 private static final long serialVersionUID = 1L;
	 private final HttpStatus httpStatus;

	public UserDoesNotExistException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}
	public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
