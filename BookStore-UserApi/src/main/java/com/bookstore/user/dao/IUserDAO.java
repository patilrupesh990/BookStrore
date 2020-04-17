package com.bookstore.user.dao;

import com.bookstore.user.model.User;

public interface IUserDAO {

	public int register(User user);
	public User isUserExist(User user);
	public int activateUSer(int id);
	public User getUser(String userName);
	public User loginUser(String email,String password);
	public User getUserById(int id);
}
