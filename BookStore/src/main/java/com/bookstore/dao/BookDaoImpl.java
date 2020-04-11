package com.bookstore.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.hibernate.StaleObjectStateException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bookstore.exception.InternalServerError;
import com.bookstore.model.Book;
import com.bookstore.util.HibernateUtil;

@Repository
public class BookDaoImpl implements IBookDAO {

	@Autowired
	HibernateUtil<Book> hibernateUtil;

	@Override
	@Transactional
	public void addBook(Book book) {
		try {
			hibernateUtil.save(book);
		} catch (Exception e) {
			throw new InternalServerError();
		}
	}

	@Override
	@Transactional
	public Book getCurrentBook(int bookCode) {
		try {
			return hibernateUtil.getBook(bookCode);
		} catch (Exception e) {
			throw new InternalServerError();
		}
	}

	@Override
	@Transactional
	public Book getBookByName(String bookName) {
		try {
			String query = "From Book where bookName=:name";

			Query<Book> book = hibernateUtil.select(query);
			book.setParameter("name", bookName);
			return book.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	@Transactional
	public int deleteBook(String bookName) {
		String query = "DELETE FROM Book where bookName=:name";
		Query<Book> hQuery = hibernateUtil.createQuery(query);
		hQuery.setParameter("name", bookName);
		return hQuery.executeUpdate();
	}

	@Override
	@Transactional
	public void updateBook(Book book, String bookName) {
		try {
			hibernateUtil.update(book);;
		} catch (StaleObjectStateException e) {
			throw new InternalServerError();
		}
	}

	@Override
	@Transactional
	public List<Book> getAllBooks() {
		try {
			String query = "FROM Book";
			Query<Book> books = hibernateUtil.select(query);
			return books.list();
		} catch (Exception e) {
			throw new InternalServerError();
		}
	}
}
