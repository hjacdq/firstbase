package com.hj.novel.task;

public class BookInfo {

	/**
	 * 书名
	 */
	private String name;
	
	/**
	 * 作者
	 */
	private String author;
	
	/**
	 * 小说首页链接
	 */
	private String indexUrl;
	
	/**
	 * 字数
	 */
	private String wordsNumber;
	
	/**
	 * 小说封面
	 */
	private String picUrl;
	
	/**
	 * 小说章节列表页面url
	 */
	private String chapterListUrl;
	
	/**
	 * 第一章url
	 */
	private String firstChapterUrl;
	
	/**
	 * 最后一章url
	 */
	private String lastChapterUrl;
	
	/**
	 * 全部章节数
	 */
	private Integer totalChapterCount;
	
	/**
	 * 开始抓取的章节的url
	 */
	private String startCatchUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIndexUrl() {
		return indexUrl;
	}

	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}

	public String getWordsNumber() {
		return wordsNumber;
	}

	public void setWordsNumber(String wordsNumber) {
		this.wordsNumber = wordsNumber;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	public String getChapterListUrl() {
		return chapterListUrl;
	}

	public void setChapterListUrl(String chapterListUrl) {
		this.chapterListUrl = chapterListUrl;
	}

	public String getFirstChapterUrl() {
		return firstChapterUrl;
	}

	public void setFirstChapterUrl(String firstChapterUrl) {
		this.firstChapterUrl = firstChapterUrl;
	}
	
	public String getLastChapterUrl() {
		return lastChapterUrl;
	}

	public void setLastChapterUrl(String lastChapterUrl) {
		this.lastChapterUrl = lastChapterUrl;
	}

	public Integer getTotalChapterCount() {
		return totalChapterCount;
	}

	public void setTotalChapterCount(Integer totalChapterCount) {
		this.totalChapterCount = totalChapterCount;
	}

	public String getStartCatchUrl() {
		return startCatchUrl;
	}

	public void setStartCatchUrl(String startCatchUrl) {
		this.startCatchUrl = startCatchUrl;
	}
	
}
