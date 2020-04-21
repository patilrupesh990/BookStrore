package com.bookstore.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bookstore.model.Order;
import com.bookstore.util.HibernateUtil;

@Repository
public class OredrDAOImpl implements IOrderDAO{
	@Autowired
	HibernateUtil<Order> hibernateUtil;
	@Override
	@Transactional
	public int addOrder(Order order) {
		try {
			hibernateUtil.save(order);
			return 1;
		} catch (Exception e) {
			return 0;
		}
		
	}
	@Override
	@Transactional
	public int deleteOrder(int bookId) {
		
		try {
			String query="DELETE FROM Order WHERE bookId=:bid";
			Query<Order> hQuery=hibernateUtil.createQuery(query);
			hQuery.setParameter("bid", bookId);
			return hQuery.executeUpdate();
		}catch (Exception e) {
			return 0;
		}
	}
	
	@Transactional
	@Override
	public List<Order> getOrderList(int userId) {
		String query="FROM Order where userId=:id";
		Query<Order> hQuery=hibernateUtil.select(query);
		hQuery.setParameter("id", userId);
		return hQuery.list();
	}
	}
