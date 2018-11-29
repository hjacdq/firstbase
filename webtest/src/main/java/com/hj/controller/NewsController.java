package com.hj.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.base.WebBaseController;
import com.hj.entity.News;
import com.hj.entity.User;
import com.hj.model.ResultMap;
import com.hj.service.NewsService;

/**
 * 发布内容
 */
@RequestMapping("/usr/newContent")
@Controller
public class NewsController extends WebBaseController{
	
	protected static final Logger logger = Logger.getLogger(NewsController.class);
	
	@Autowired
	private NewsService newsService;
	
	
	/**
	 * @param req
	 * @param res
	 * @return
	 * 保存内容
	 */
	@ResponseBody
	@RequestMapping("/saveContent")
	public ResultMap save(HttpServletRequest req,News news){
		try{
			User user  = (User)getSession(req,"user");
			if(user==null)
				return ResultMap.failure("请登录后再发布内容");
			if(news==null)
				return ResultMap.failure("参数为空");
			
			return newsService.doSave(news, user);
		}catch(Exception e){
			logger.error("NewsController.save error ",e);
			return ResultMap.failure("系统异常");
		}
	}
	
	/**
	 * @param req
	 * @param news
	 * @return
	 * 获取热门话题
	 */
	@ResponseBody
	@RequestMapping("/getHotList")
	public List<Map<String,Object>> getHotList(HttpServletRequest req,News news){
		try{
			StringBuffer url = req.getRequestURL();
			String uri  = req.getRequestURI();
			System.out.println("url="+url+" uri="+uri);
			return newsService.getHotList();
		}catch(Exception e){
			logger.error("NewsController.getHotList error ",e);
			return null;
		}
	}
	
}
