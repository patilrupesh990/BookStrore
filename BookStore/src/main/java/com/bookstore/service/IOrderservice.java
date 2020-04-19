package com.bookstore.service;

import org.springframework.http.ResponseEntity;

public interface IOrderservice {

	public ResponseEntity<Object> makeOrder(String token,int id,int quantity);
	public ResponseEntity<Object> cancelOrder();
	
}
