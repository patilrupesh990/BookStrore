package com.bookstore.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bookstore.model.Order;

public interface IOrderservice {

	public ResponseEntity<Object> makeOrder(String token,int id,int quantity);
	public ResponseEntity<Object> cancelOrder(String token,int bookId);
	public ResponseEntity<Object> getCartList(String token);
	public ResponseEntity<Object> updateQuantity(String token,Order order) ;
	public ResponseEntity<Object> confirmOrder(String token,List<Order> order);
}
