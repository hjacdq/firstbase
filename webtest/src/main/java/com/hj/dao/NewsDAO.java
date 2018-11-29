package com.hj.dao;

import java.util.List;

import com.hj.entity.News;

public interface NewsDAO {
	
	public void save(News news);

	public News getById(Long id);
	
	public List<News> getByAuthor(String authorName);
	
	public int update(News news);
	
}
