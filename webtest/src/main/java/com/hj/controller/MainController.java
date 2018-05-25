package com.hj.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hj.base.WebBaseController;
import com.hj.service.impl.RedisClientTemplate;

@RequestMapping("/main")
@Controller
public class MainController extends WebBaseController{
	
	
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	/**
	 * 跳转主页
	 * @return
	 */
	@RequestMapping("/index")
	public String toIndex(HttpServletRequest req,HttpServletResponse res){
		return "index";
	}
	
	@RequestMapping("/login")
	public String toLogin(HttpServletRequest req,HttpServletResponse res){
		return "login";
	}
	
	
	@RequestMapping("/toTest")
	public ModelAndView toTest(HttpServletRequest req,HttpServletResponse res){
		ModelAndView mav = new ModelAndView("test");
		return mav;
	}
	
	@RequestMapping("/toChat")
	public ModelAndView toChat(HttpServletRequest req,HttpServletResponse res){
		ModelAndView mav = new ModelAndView("chat");
		return mav;
	}
	
	@RequestMapping("/toComment")
	public ModelAndView toComment(HttpServletRequest req,HttpServletResponse res){
		ModelAndView mav = new ModelAndView("comment");
		return mav;
	}
	
	@RequestMapping("/toUpload")
	public ModelAndView toUpload(HttpServletRequest req,HttpServletResponse res){
		ModelAndView mav = new ModelAndView("upload");
		return mav;
	}
	
	@RequestMapping("/toDownload")
	public ModelAndView toDownload(HttpServletRequest req,HttpServletResponse res){
		ModelAndView mav = new ModelAndView("download/index");
		return mav;
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest req,HttpServletResponse res){
		req.getSession().removeAttribute("userId");
		req.getSession().removeAttribute("username");
		ModelAndView mav = new ModelAndView("redirect:/main/index.do");
		return mav;
	}
	
	@RequestMapping("/getmsg")
	public ModelAndView getMsg(HttpServletRequest req,HttpServletResponse res){
		ModelAndView mav = new ModelAndView("index");
		String arg1 = (String)req.getParameter("arg1");
		String arg2 = (String)req.getParameter("arg2");
		String arg3 = (String)req.getParameter("arg3");

		System.out.println(arg1);
		System.out.println(arg2);
		System.out.println(arg3);
		if(arg1 == null || arg1=="" || "".equals(arg1)){
			arg1="helloworld<script>alert('hello world');</script>";
        }
		arg1 = StringEscapeUtils.escapeHtml(arg1);
		System.out.println(arg1);
		mav.addObject("arg1", arg1);
		return mav;
	}
	
	public static void main(String[] args) {
		String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";  
		String phone = "15261145513";
		System.out.println(phone.matches(regex));
	}
}
