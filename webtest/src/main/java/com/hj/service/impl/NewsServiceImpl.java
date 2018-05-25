package com.hj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.dao.NewsDAO;
import com.hj.entity.News;
import com.hj.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService{

	@Autowired
	private NewsDAO newsDAO;
	
	public News getById(Long id) {
		return newsDAO.getById(id);
	}

	@Override
	public Long save(News news) {
		return newsDAO.save(news);
	}

	@Override
	public List<News> getByAuthor(String authorName) {
		return newsDAO.getByAuthor(authorName);
	}

	@Override
	public int update(News news) {
		return newsDAO.update(news);
	}

}
