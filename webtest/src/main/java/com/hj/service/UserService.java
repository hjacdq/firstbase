package com.hj.service;

import com.hj.entity.User;

public interface UserService {
	
	public void addUser(User user);
	
	public int delete(String openid); 
	
	public User getByUsername(String username);
	
	public int deleteByUsername(String username);
	
	public Integer update(User user);
	
}
