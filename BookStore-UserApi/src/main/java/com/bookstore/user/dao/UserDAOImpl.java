package com.bookstore.user.dao;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.user.model.User;
import com.bookstore.user.util.HibernateUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDAOImpl implements IUserDAO {

	@Autowired
	HibernateUtil<User> hibernateUtil;

	@Transactional
	@Override
	public int register(User user) {
		try {
			hibernateUtil.save(user);
			return 1;
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}

	@Transactional
	@Override
	public User isUserExist(User user) {
		String query = "From User where email=:uemail or userName=:uname";
		try {
			Query<User> hQuery = hibernateUtil.createQuery(query);
			hQuery.setParameter("uemail", user.getEmail());
			hQuery.setParameter("uname", user.getUserName());
			return hQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public int activateUSer(int id) {
		String query = "update User set activate=:activ" + " " + " " + " where uId = :id";

		Query<User> hQuery = hibernateUtil.createQuery(query);
		hQuery.setParameter("activ", true);
		hQuery.setParameter("id", id);
		return hQuery.executeUpdate();
	}

	@Override
	public User getUser(String email) {
		try {
			String query = "FROM User where email=:eml OR userName=:uname";
			Query<User> hquery = hibernateUtil.createQuery(query);
			hquery.setParameter("eml", email);
			hquery.setParameter("uname", email);
			return hquery.getSingleResult();
		} catch (NoResultException e) {
			System.out.println(e);
			return null;
		}

	}

	@Override
	public User loginUser(String email, String password) {
		log.info("login User Dao" + email + password);
		String query = "FROM User where email=:eml AND password=:psw";
		try {
			Query<User> hquery = hibernateUtil.createQuery(query);
			hquery.setParameter("eml", email);
			hquery.setParameter("psw", password);
			log.info("User" + hquery.getSingleResult());
			return hquery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
