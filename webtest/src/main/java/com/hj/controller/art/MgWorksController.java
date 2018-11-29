package com.hj.controller.art;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hj.base.WebBaseController;
import com.hj.model.ResultMap;
import com.hj.service.art.ArtMgWordsService;
import com.hj.util.StringUtil;

/**
 * 敏感词
 */
@RequestMapping("/art/mgwords")
@Controller
public class MgWorksController extends WebBaseController{
	
	protected static final Logger logger = Logger.getLogger(MgWorksController.class);
	
	@Autowired
	private ArtMgWordsService artMgWordsService;
	
	/**
	 * @param req
	 * @param res
	 * @return
	 * 跳转新增页面
	 */
	@RequestMapping("/toAddWords")
	public ModelAndView toAddWords(HttpServletRequest req,HttpServletResponse res){
		ModelAndView mav = new ModelAndView("art/add_words");
		return mav;
	}
	
	/**
	 * @param req
	 * @param res
	 * @return
	 * 保存敏感词
	 */
	@ResponseBody
	@RequestMapping("/save")
	public ResultMap save(HttpServletRequest req,HttpServletResponse res){
		String keywordsStr = (String)req.getParameter("keywordsStr").trim();
		if(StringUtil.isEmpty(keywordsStr))
			return ResultMap.failure("参数为空");
		return artMgWordsService.doSave(keywordsStr);
	}
	

	/**
	 * @param req
	 * @param res
	 * @return
	 * 跳转检测敏感词页面
	 */
	@RequestMapping("/toCheck")
	public ModelAndView toCheck(HttpServletRequest req,HttpServletResponse res){
		ModelAndView mav = new ModelAndView("art/checkPage");
		return mav;
	}
	
	/**
	 * @param req
	 * @param res
	 * @return
	 * 检查敏感词
	 */
	@ResponseBody
	@RequestMapping("/check")
	public ResultMap check(HttpServletRequest req,HttpServletResponse res){
		StringBuffer url = req.getRequestURL();
//		if(url.toString().indexOf("hjacdq.top")<0)
//			return ResultMap.failure("请求异常");
		
		String content = (String)req.getParameter("content").trim();
		if(StringUtil.isEmpty(content))
			return ResultMap.failure("参数为空");
		return artMgWordsService.check(content);
	}
	
	
	
}
