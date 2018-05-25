package com.hj.service;

import java.util.List;

import com.hj.entity.UserInfo;

public interface UserInfoService {
	
	public void save(UserInfo userInfo);
	
	public int delete(Long id); 
	
	public int deleteByUserId(Long userId);
	
	public List<UserInfo> getByUserId(Long userId);
	
	public int update(UserInfo userInfo);
	
}
