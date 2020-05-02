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
	
	@Override
	@Transactional
	public int removeAllOrder(int userId) {
		
		try {
			String query="DELETE FROM Order WHERE userId=:uid";
			Query<Order> hQuery=hibernateUtil.createQuery(query);
			hQuery.setParameter("uid", userId);
			return hQuery.executeUpdate();
		}catch (Exception e) {
			return 0;
		}
	}

	
	
	@Transactional
	@Override
	public int updateQuantity(Order order) {
		try {
		String query="UPDATE Order SET quantity=:qty,total=:total WHERE OrderId=:id";
		Query<Order> hQuery=hibernateUtil.createQuery(query);
		hQuery.setParameter("qty", order.getQuantity());
		hQuery.setParameter("id", order.getOrderId());
		hQuery.setParameter("total", order.getQuantity()*order.getPrice());
		return hQuery.executeUpdate();
		}catch (Exception e) {
			System.out.println();
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
