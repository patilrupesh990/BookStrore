package com.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.service.IOrderservice;

@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
public class ShoppingCardController {

	@Autowired
	IOrderservice orderService;
	
	@PostMapping("/make-order")
	public ResponseEntity<Object> addBook(@RequestHeader String token ,@RequestParam("bookId") int id,@RequestParam("qty") int quantity){
		return orderService.makeOrder(token,id,quantity);
	}
}
