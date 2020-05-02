package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.Order;
import com.bookstore.service.IOrderservice;

@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
public class ShoppingCardController {

	@Autowired
	IOrderservice orderService;
	
	@PostMapping("/make-order")
	public ResponseEntity<Object> addOrder(@RequestHeader String token ,@RequestParam("bookId") int id,@RequestParam("qty") int quantity){
		return orderService.makeOrder(token,id,quantity);
	}
	@DeleteMapping("/remove-order")
	public ResponseEntity<Object> removeOrder(@RequestHeader String token ,@RequestParam("bookId") int id){
		return orderService.cancelOrder(token, id);
	}
	
	@GetMapping("/cart-list")
	public ResponseEntity<Object> getCartList(@RequestHeader String token){
		return orderService.getCartList(token);
	}
	@PutMapping("/update-quantity")
	public ResponseEntity<Object> updateBookQuantity(@RequestHeader String token,@RequestBody Order order){
		System.out.println(order.getQuantity());
		return orderService.updateQuantity(token, order);
	}
	
	@PutMapping("/confirm-order")
	public ResponseEntity<Object> confirmOrder(@RequestBody List<Order> order,@RequestHeader String token) {
		return orderService.confirmOrder(token,order);
	}


	
}
