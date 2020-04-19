package com.bookstore.user.response;

import lombok.Data;

@Data
public class LoginSuccessResponse {
	String firstName;
	String lastName;
	String token;
	String response;
	int status;
	public LoginSuccessResponse(){
		
	}
	public LoginSuccessResponse(String firstName, String lastName, String token, String response, int status) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.token = token;
		this.response = response;
		this.status = status;
	}
	
	
}
