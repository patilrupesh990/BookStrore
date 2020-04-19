package com.bookstore.dao;

import com.bookstore.model.Order;

public interface IOrderDAO {
	public int addOrder(Order order);
	public int deleteOrder(int bookId);
}
