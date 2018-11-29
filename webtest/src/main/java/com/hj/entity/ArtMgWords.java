package com.hj.entity;

import java.io.Serializable;

/**
 * 敏感词
 */
public class ArtMgWords extends BaseDO implements Serializable{

	private static final long serialVersionUID = -8419991154191549008L;

	private String code;
	
	private String keywords;
	
	private String index;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
	
}
