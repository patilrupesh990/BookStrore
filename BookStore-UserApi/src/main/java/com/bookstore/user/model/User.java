package com.bookstore.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@Table(name = "user_details")
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uId;
	@Column(name = "FIRST_NAME")
	@NotBlank(message = "First Name is mandatory")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "PASSWORD")
	@NotBlank(message = "Password is mandatory")
	private String password;
	@Column(name = "EMAIL", unique = true)
	@NotNull
	private String email;

	@Column(name = "GENDER")
	@NotBlank(message = "Gender is mandatory")
	private String gender;

	@Column(name = "MO_No")
	@NotBlank(message = "contact is mandatory")
	private String phNo;

	@Column(name = "USER_NAME")
	@NotBlank(message = "UserName is mandatory")
	private String userName;

	@Column(name = "REGISTRATION_DATE")
	@NotNull
	private String creationTime;

	@Column(name = "LAST_UPDATE")
	private String updateTime;

	@Column(name = "Verified")
	@NotNull
	private boolean activate;

}
