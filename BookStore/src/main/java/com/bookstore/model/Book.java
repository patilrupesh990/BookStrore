package com.bookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
@Table(name="books_details")
@Entity
@ToString
public class Book {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookId;
	
	@Column
	@NotNull
	private int userId;
	
	@Column
	@NotNull
	private String bookName;
	
	@Column
	private String bookCode;

	@Column
	@NotNull
	private String bookDetails;
	
	@Column
	@NotNull
	private Double price;
	
	@Column
	@NotNull
	private int quantity;
	
	@Column
	@NotNull
	private String authorName;
	
	@Column
	private String bookImage;
	
}
