package com.hj.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.dao.NewsDAO;
import com.hj.entity.News;
import com.hj.entity.User;
import com.hj.model.ResultMap;
import com.hj.service.NewsService;
import com.hj.util.StringUtil;

@Service
public class NewsServiceImpl implements NewsService{

	@Autowired
	private NewsDAO newsDAO;
	
	public News getById(Long id) {
		return newsDAO.getById(id);
	}
	
	public void save(News news) {
		newsDAO.save(news);
	}

	public List<News> getByAuthor(String authorName) {
		return newsDAO.getByAuthor(authorName);
	}

	public int update(News news) {
		return newsDAO.update(news);
	}

	@Override
	public ResultMap doSave(News news, User user) {
		if(StringUtil.isEmpty(news.getTitle()) || StringUtil.isEmpty(news.getContent()))
			return ResultMap.failure("请输入标题和内容");
		if(news.getTitle().trim().length()>50)
			return ResultMap.failure("标题长度不能超过50");
		news.setAuthor(user.getCode());
		news.setType("usr");
		save(news);
		return ResultMap.success();
	}
	
	@Override
	public List<Map<String, Object>> getHotList() {
		
		return null;
	}

}
