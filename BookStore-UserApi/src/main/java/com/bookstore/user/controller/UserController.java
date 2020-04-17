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
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.user.model.User;
import com.bookstore.user.response.UserData;
import com.bookstore.user.response.UserResponse;
import com.bookstore.user.service.IUserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	IUserService service;

	@PostMapping("/register")
	public ResponseEntity<UserResponse> registerUser(@RequestBody User user){
		user.setSeller(false);
		return service.register(user);
	}
	
	@PostMapping("/seller-register")
	public ResponseEntity<UserResponse> sellerRegister(@RequestBody User user){
		user.setSeller(true);
		return service.register(user);
	}
	
	
	@GetMapping("/activ/{token}")
	public ResponseEntity<UserResponse> activateUserAccount(@PathVariable("token")String token){
		return service.activateUser(token);
	}
	
	@PostMapping("/login/{email}")
	public ResponseEntity<Object> loginUser(@PathVariable("email") String email,@RequestHeader("password") String password) {
		return service.loginUser(email,password);
	}
	
	@PostMapping("/seller-login/{email}")
	public ResponseEntity<Object> sellerLogin(@PathVariable("email") String email,@RequestHeader("password") String password) {
		return service.loginAdmin(email,password);
	}
	
	@GetMapping("/{token}")
	public ResponseEntity<UserData> getUserById(@PathVariable("token") String token) {
		return service.getUserByID(token);
	}
	
}
