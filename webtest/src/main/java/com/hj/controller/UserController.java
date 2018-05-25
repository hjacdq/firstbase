package com.hj.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hj.base.WebBaseController;
import com.hj.entity.User;
import com.hj.entity.UserInfo;
import com.hj.service.UserInfoService;
import com.hj.service.UserService;
import com.hj.service.impl.RedisClientTemplate;
import com.hj.util.MD5Util;
import com.hj.util.StringUtil;

@Controller
@RequestMapping("/user")
public class UserController extends WebBaseController{
	
	protected static final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	/** 
	* @Title: mine
	* @Description: TODO(个人中心)
	* @param req
	* @param res
	* @return
	* ModelAndView
	* @throws 
	* @creator     :hj
	* @create date :2017年9月28日 上午10:01:42
	* @modificator :hj
	* @modify date :2017年9月28日 上午10:01:42
	*/ 
	@RequestMapping("/mine")
	public ModelAndView mine(HttpServletRequest req,HttpServletResponse res){
		ModelAndView mav = getUserModel("mine");
		Long userId = (Long)getSession(req,"userId");
		String username = (String)getSession(req,"username");
		if(userId==null || StringUtil.isBlank(username)){
			return new ModelAndView("redirect:/main/index.do");
		}
		User user = userService.getByUsername(username);
		if(user!=null){
			List<UserInfo> infoList = userInfoService.getByUserId(user.getId());
			if(infoList!=null && infoList.size()>0){
				mav.addObject("userInfo", infoList.get(0));
			}
		}
		mav.addObject("user", user);
		
		return mav;
		
	}
	
	/**
	 * 
	* @Title: regist
	* @Description: TODO(注册)
	* @param req
	* @param res
	* @return
	* Map<String,Object>
	* @throws 
	* @creator     :hj
	* @create date :2017年9月28日 上午9:30:37
	* @modificator :hj
	* @modify date :2017年9月28日 上午9:30:37
	 */
	@ResponseBody
	@RequestMapping("/regist")
	public Map<String,Object> regist(HttpServletRequest req,HttpServletResponse res){
		Map<String,Object> result = new HashMap<String,Object>();
		String username = (String)req.getParameter("username").trim();
		String password = (String)req.getParameter("password").trim();
		String password2 = (String)req.getParameter("password2").trim();
		if(StringUtil.isBlank(username) || StringUtil.isBlank(password)){
			result.put("status", "error");
			result.put("msg", "账号密码不能为空!");
			return result;
		}else if( !password.equals(password2)){
			result.put("status", "error");
			result.put("msg", "两次输入的密码要一致!");
			return result;
		}
		User user = userService.getByUsername(username);
		if(user!=null){
			result.put("status", "error");
			result.put("msg", "账号已存在，请直接登录");
			return result;
		}
		if(username.length()>20){
			result.put("status", "error");
			result.put("msg", "账号过长");
			return result;
		}
		if(password.length()<6){
			result.put("status", "error");
			result.put("msg", "密码太短");
			return result;
		}else if(password.length()>18){
			result.put("status", "error");
			result.put("msg", "密码太长");
			return result;
		}
		user = new User();
		user.setUsername(username);
		password = MD5Util.string2MD5(password);
		user.setPassword(password);
		userService.addUser(user);
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("USER_STATUS", "login");
		result.put("status", "success");
		return result;
	}
	
	/** 
	* @Title: toLogin
	* @Description: TODO(用户登录)
	* @param req
	* @param res
	* @return
	* ModelAndView
	* @throws 
	* @creator     :hj
	* @create date :2017年1月9日 下午3:22:01
	* @modificator :hj
	* @modify date :2017年1月9日 下午3:22:01
	*/ 
	@ResponseBody
	@RequestMapping("/login")
	public Map<String,Object> toLogin(HttpServletRequest req,HttpServletResponse res){
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> params = PageData(req);
		String username = StringUtil.trim((String)params.get("username"));
		String password = StringUtil.trim((String)params.get("password"));
		if(StringUtil.isBlank(username) || StringUtil.isBlank(password)){
			result.put("status", "error");
			result.put("msg", "用户名和密码不能为空");
			return result;
		}
		String regex = "[%-`~!@#$^&*()=|{}':;',\\.<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]";
		List<String> regList = Arrays.asList(regex.split(""));
		boolean flag = false;
		String str="";
		for(int i=0;i<username.length();i++){
			String s = username.substring(i,i+1);
			if(regList.contains(s)){
				flag = true;
				str = s;
				break;
			}
		}
		if(flag){
			result.put("status", "error");
			result.put("msg", "用户名含有特殊字符"+str);
			return result;
		}
		User user = userService.getByUsername(username);
		if(user==null){
			result.put("status", "error");
			result.put("msg", "账号不存在");
			return result;
		}
		if(!MD5Util.string2MD5(password).equals(user.getPassword())){
			result.put("status", "error");
			result.put("msg", "密码错误");
			return result;
		}
		setSession(req,user);
		req.getSession().setAttribute("USER_STATUS", "login");
		Cookie cookie = new Cookie("username",user.getUsername());  
        cookie.setMaxAge(1000*60*60*24*30*12);  
		result.put("status", "success");
		return result;
	}
	
	/**
	 * @param req
	 * @param res
	 * @return
	 * 更换用户头像
	 */
	@ResponseBody
	@RequestMapping("/changePicUrl")
	public Map<String,Object> changePicUrl(HttpServletRequest req,HttpServletResponse res){
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> params = PageData(req);
		String username = (String)req.getSession().getAttribute("username");
		String picUrl = StringUtil.trim((String)params.get("picUrl"));
		User user = userService.getByUsername(username);
		user.setHeadimgurl(picUrl);
		userService.update(user);
		result.put("errorCode", 0);
		return result;
	}
	
	
}
