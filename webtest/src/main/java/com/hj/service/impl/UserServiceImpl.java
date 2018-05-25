package com.hj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hj.dao.UserDAO;
import com.hj.entity.User;
import com.hj.service.UserService;

@Transactional
@Service(value="userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDAO userDAO;
	
	
	public void addUser(User user) {
		userDAO.save(user);
	}

	public int delete(String openId) {
		return userDAO.delete(openId);
	}

	public User getByUsername(String username) {
		return userDAO.getByUsername(username);
	}

	public int deleteByUsername(String username) {
		return userDAO.deleteByUsername(username);
	}

	@Override
	public Integer update(User user) {
		return userDAO.update(user);
	}
	
}
