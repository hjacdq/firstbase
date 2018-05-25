package com.hj.service;

import java.util.List;

import com.hj.entity.News;

public interface NewsService {
	
	public Long save(News news);
	
	public News getById(Long id);

	public List<News> getByAuthor(String authorName);
	
	public int update(News news);
}
