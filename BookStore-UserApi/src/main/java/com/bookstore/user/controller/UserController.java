package com.bookstore.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.user.model.User;
import com.bookstore.user.response.UserResponse;
import com.bookstore.user.service.IUserService;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	IUserService service;
	
	@PostMapping("/register")
	public ResponseEntity<UserResponse> registerUser(@RequestBody User user){
		return service.register(user);
	}
	
	@GetMapping("/activ/{token}")
	public ResponseEntity<UserResponse> activateUserAccount(@PathVariable("token")String token){
		return service.activateUser(token);
	}
	
	@PostMapping("/login/{email}")
	public ResponseEntity<UserResponse> loginUser(@PathVariable("email") String email,@RequestHeader("password") String password) {
		return service.loginUser(email,password);
	}
}
