package com.hj.dao;

import com.hj.entity.Member;

public interface MemberDAO {

	public Member getByPhone(String phone);
	
	public Member getByOpenid(String openid);
	
	public int update(Member member);
	
	
}
