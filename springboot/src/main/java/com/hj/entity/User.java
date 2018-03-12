package com.hj.entity;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.annotation.JSONField;

@Component
public class User {

	private Integer id;
    
    private String username;
    
    private String password;
    
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date birthday;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
    
    
}
