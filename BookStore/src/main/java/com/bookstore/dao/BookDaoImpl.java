package com.bookstore.dao;

import javax.persistence.NoResultException;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bookstore.model.Book;
import com.bookstore.util.HibernateUtil;

@Repository
public class BookDaoImpl implements IBookDAO {

	@Autowired
	HibernateUtil<Book> hibernateUtil;

	@Override
	public void addBook(Book book) {
		hibernateUtil.save(book);
	}

	@Override
	public Book getCurrentBook(int bookCode) {
		return hibernateUtil.getBook(bookCode);
	}

	@Override
	public Book getBookByName(String bookName) {
		try {
		String query = "From Book where bookName=:name";

		Query<Book> book = hibernateUtil.select(query);
		book.setParameter("name", bookName);
		return book.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
	}
}
