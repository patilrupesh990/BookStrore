package com.bookstore.response;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class OrderResponse {
	int statusCode;
	String response;
	
	public OrderResponse() {}

	public OrderResponse(int statusCode, String response) {
		super();
		this.statusCode = statusCode;
		this.response = response;
	}
	
	

}
