package com.hj.service;

import com.hj.entity.Member;

public interface MemberService {
	
	public Member getByPhone(String phone);

	public Member getByOpenid(String openid);
	
	public int update(Member member);
}
