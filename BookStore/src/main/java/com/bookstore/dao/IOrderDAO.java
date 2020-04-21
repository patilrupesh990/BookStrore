package com.bookstore.dao;

import java.util.List;

import com.bookstore.model.Order;

public interface IOrderDAO {
	public int addOrder(Order order);

	public int deleteOrder(int bookId);

	public List<Order> getOrderList(int userId);
}
