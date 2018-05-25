package com.hj.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.WebSocketSession;

import com.hj.webSocket.WebSocketHander;

@Controller
@RequestMapping("/webSocket")
public class WebSocketController {

	@RequestMapping("/socketPage")
	public ModelAndView socketPage(HttpServletRequest req,HttpServletResponse res){
		ModelAndView mav = new ModelAndView("socketPage");
		String userId = getUUID();
		req.getSession().setAttribute("userId", userId);
		return mav;
	}
	
	private static String getUUID(){
		 UUID uuid = UUID.randomUUID();
		 return uuid.toString();
	}
	
}
