package com.bookstore.dao;

import java.util.List;

import com.bookstore.model.Book;

public interface IBookDAO {
	public void addBook(Book book);
	public void updateBook(Book book,String bookName);
	public Book getCurrentBook(int bookName);
	public Book getBookByName(String bookName);
	public int deleteBook(String bookName);
	public List<Book> getAllBooks();
}
