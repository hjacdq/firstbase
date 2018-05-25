package com.hj.dao;

import java.util.List;

import com.hj.entity.User;

public interface UserDAO {

	public void save(User user);
	
	public List<User> getAll();
	
	public User getByOpenId(String openId);
	
	public int delete(String openId);
	
	public User getByUsername(String username);
	
	public int deleteByUsername(String username);
	
	public int update(User user);
	
}
