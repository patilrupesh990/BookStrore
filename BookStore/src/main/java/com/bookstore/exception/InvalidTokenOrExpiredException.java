package com.bookstore.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenOrExpiredException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final HttpStatus httpStatus;

	public InvalidTokenOrExpiredException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
