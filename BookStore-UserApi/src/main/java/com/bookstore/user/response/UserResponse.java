package com.bookstore.user.response;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bookstore.user.model.User;

import lombok.Data;

@Component
@Data
public class UserResponse {

	User user;
	int status;
	String response;

	List<User> usersList;

	
	public UserResponse() {
		}
	public UserResponse(User user, int status, String response) {
		super();
		this.user = user;
		this.status = status;
		this.response = response;
	}

	public UserResponse(int status, String response) {
		super();
		this.status = status;
		this.response = response;
	}

	public UserResponse(int status, String response, List<User> usersList) {
		super();
		this.status = status;
		this.response = response;
		this.usersList = usersList;
	}

}
