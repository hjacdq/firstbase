package com.hj.entity;

import java.io.Serializable;

/**
 * @author Administrator
 * 会员
 */
public class Member extends BaseDO implements Serializable{

	private static final long serialVersionUID = -4570983086917585796L;

	private String name;
	
	private String username; 
	
	private String password; 
	
	private String nickName; //昵称
	
	private String picUrl; //头像地址
	
	private String phone;
	
	private Integer integral;//几分
	
	private String sex;//值为1时是男性，值为2时是女性，值为0时是未知
	
	private String openId;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getPicUrl() {
		return picUrl;
	}
	
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Integer getIntegral() {
		return integral;
	}
	
	public void setIntegral(Integer integral) {
		this.integral = integral;
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
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getOpenId() {
		return openId;
	}
	
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
}
