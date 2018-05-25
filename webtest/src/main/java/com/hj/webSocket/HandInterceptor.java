package com.hj.webSocket;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * @author Administrator
 *  拦截器（握手）
 * 
 */
public class HandInterceptor implements HandshakeInterceptor{

	@Override
	public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse arg1, WebSocketHandler arg2,
			Map<String, Object> map) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
            // 使用userName区分WebSocketHandler，以便定向发送消息
            String userId = (String) req.getSession().getAttribute("userId");
            map.put("userId", userId);
        }
        return true;
	}

}
