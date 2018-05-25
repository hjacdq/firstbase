package com.hj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.dao.MemberDAO;
import com.hj.entity.Member;
import com.hj.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberDAO memberDAO;
	
	public Member getByOpenid(String openid) {
		return memberDAO.getByOpenid(openid);
	}

	public int update(Member member) {
		return memberDAO.update(member);
	}

	public Member getByPhone(String phone) {
		return memberDAO.getByPhone(phone);
	}

}
