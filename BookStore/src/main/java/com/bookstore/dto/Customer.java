package com.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	private String customerName;
	private String contact;
	private String pinCode;
	private String locality;
	private String city;
	private String landMark;
	private String address;
}
