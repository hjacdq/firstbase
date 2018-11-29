package com.hj.service;

import java.util.List;
import java.util.Map;

import com.hj.entity.News;
import com.hj.entity.User;
import com.hj.model.ResultMap;

public interface NewsService {
	
	public ResultMap doSave(News news,User user);
	
	/**
	 * @return
	 * 获取热门话题
	 */
	List<Map<String,Object>> getHotList();
	
}
