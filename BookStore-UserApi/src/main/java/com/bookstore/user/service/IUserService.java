package com.bookstore.user.service;

import org.springframework.http.ResponseEntity;

import com.bookstore.user.model.User;
import com.bookstore.user.response.UserData;
import com.bookstore.user.response.UserResponse;

public interface IUserService {
	public ResponseEntity<UserResponse> register(User user);
	public ResponseEntity<UserResponse> activateUser(String token);
	public ResponseEntity<Object> loginUser(String email, String password);
	public ResponseEntity<UserData> getUserByID(String token);
	public ResponseEntity<Object> loginAdmin(String email, String password);
}
