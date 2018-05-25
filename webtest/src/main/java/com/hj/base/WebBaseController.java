package com.hj.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.hj.entity.User;
import com.hj.util.StringUtil;

public class WebBaseController {
	
	public ModelAndView getPageModel(String viewName){
		ModelAndView mav = new ModelAndView(viewName);
		return mav;
	}

	public ModelAndView getUserModel(String viewName){
		ModelAndView mav = new ModelAndView("user/"+viewName);
		return mav;
	}
	
	public Map<String,Object> PageData(HttpServletRequest request){
		Map<String,String[]> properties = request.getParameterMap();
	    Map<String,Object> returnMap = new HashMap<String,Object>(); 
	    Iterator entries = properties.entrySet().iterator(); 
	    Map.Entry entry; 
	    String name = "";  
	    String value = "";  
	    while (entries.hasNext()) {
	      entry = (Map.Entry) entries.next(); 
	      name = (String) entry.getKey(); 
	      Object valueObj = entry.getValue(); 
	      if(null == valueObj){ 
	        value = ""; 
	      }else if(valueObj instanceof String[]){ 
	        String[] values = (String[])valueObj;
	        for(int i=0;i<values.length;i++){ 
	           value = values[i] + ",";
	        }
	        value = value.substring(0, value.length()-1); 
	      }else{
	        value = valueObj.toString(); 
	      }
	      returnMap.put(name, value); 
	    }
	    return  returnMap;
	}
	
	
	public void setSession(HttpServletRequest request,User user){
		if(user==null){
			return;
		}
		Long userId = user.getId();
		String username = user.getUsername();
		request.getSession().setAttribute("userId", userId);
		request.getSession().setAttribute("username", username);
		
	}
	
	public Object getSession(HttpServletRequest request,String attrName){
		if(StringUtil.isBlank(attrName)){
			return null;
		}
		return request.getSession().getAttribute(attrName);
	}
	
}
