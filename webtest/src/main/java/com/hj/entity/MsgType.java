package com.hj.entity;

public enum MsgType {
	
	Text("text"),
	Image("image"),
	Voice("voice"),
	Video("video"),
	Location("location"),
	Link("link");
	
	private String name;
	
	private MsgType(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
}
