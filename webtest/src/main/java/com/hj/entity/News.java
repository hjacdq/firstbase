package com.hj.entity;

import java.io.Serializable;

public class News extends BaseDO implements Serializable{
	
	private static final long serialVersionUID = -8784301459227269974L;

	private String type;
	
	private String title;
	
	private String content;
	
	private String author;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
