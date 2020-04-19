package com.bookstore.model;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.ToString;

@Data
@Component
@ToString
public class UserData {
	private int uId;
	private String firstName;	
	private String lastName;
	private String password;
	private String email;
	private String gender;
	private Long phNo;
	private String userName;
	private String creationTime;
	private String updateTime;
	private boolean activate;
	private int status;
	private String response;

	public UserData(int status, String response) {
		this.status = status;
		this.response = response;
	}

	public UserData() {
	}
}
