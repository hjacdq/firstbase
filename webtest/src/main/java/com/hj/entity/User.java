package com.hj.entity;

import java.io.Serializable;

public class User extends BaseDO implements Serializable{

	private static final long serialVersionUID = -1301086892101400435L;

	private String username;
	
	private String password;
	
	private String code;//用户的唯一标识
	
	private String nickname;//用户昵称
	
	private String sex;//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	
	private String province;//用户个人资料填写的省份
	
	private String city;//普通用户个人资料填写的城市
	
	private String country;//国家，如中国为CN
	
	//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像）
	//，用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	private String headimgurl;
	
	private String privilege;//用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
	
	private String unionid;//只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", code=" + code + ", nickname=" + nickname
				+ ", sex=" + sex + ", province=" + province + ", city=" + city
				+ ", country=" + country + ", headimgurl=" + headimgurl
				+ ", privilege=" + privilege + ", unionid=" + unionid
				+ ", createTime=" + createTime + ", modifyTime=" + modifyTime
				+ "]";
	}
	
}
