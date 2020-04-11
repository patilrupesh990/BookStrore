package com.bookstore.dao;

import com.bookstore.model.Book;

public interface IBookDAO {
	public void addBook(Book book);
	public Book getCurrentBook(int bookName);
	public Book getBookByName(String bookName);
}
