package com.hj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.dao.UserInfoDAO;
import com.hj.entity.UserInfo;
import com.hj.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService{

	@Autowired
	private UserInfoDAO userInfoDAO;
	
	@Override
	public void save(UserInfo userInfo) {
		userInfoDAO.save(userInfo);
	}

	@Override
	public int delete(Long id) {
		return userInfoDAO.delete(id);
	}

	@Override
	public int deleteByUserId(Long userId) {
		return userInfoDAO.deleteByUserId(userId);
	}

	@Override
	public List<UserInfo> getByUserId(Long userId) {
		return userInfoDAO.getByUserId(userId);
	}

	@Override
	public int update(UserInfo userInfo) {
		return userInfoDAO.update(userInfo);
	}

}
