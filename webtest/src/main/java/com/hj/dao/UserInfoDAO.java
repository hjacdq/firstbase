package com.hj.dao;

import java.util.List;

import com.hj.entity.UserInfo;

public interface UserInfoDAO {

	public void save(UserInfo userInfo);
	
	public List<UserInfo> getByUserId(Long userId);
	
	public int delete(Long id);
	
	public int deleteByUserId(Long userId);
	
	public int update(UserInfo userInfo);
	
}
