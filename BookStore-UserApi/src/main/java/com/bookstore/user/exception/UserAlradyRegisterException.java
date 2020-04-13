package com.bookstore.user.exception;

import org.springframework.http.HttpStatus;

public class UserAlradyRegisterException extends RuntimeException {
	 private static final long serialVersionUID = 1L;
	    private final HttpStatus httpStatus;

	    public UserAlradyRegisterException( String message, HttpStatus httpStatus ) {
	        super (message);
	        this.httpStatus = httpStatus;
	    }

	    public HttpStatus getHttpStatus() {
	        return httpStatus;
	    }
}
