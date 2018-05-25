package com.hj.webSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * EnableWebSocket 开启websocket
 *
 * @EnableWebMvc //这个标注可以不加，如果有加，要extends WebMvcConfigurerAdapter
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 支持websocket 的访问链接
        registry.addHandler(new WebSocketHander(), "/chat.do")
                .addInterceptors(new HandInterceptor());
        // 不支持websocket的访问链接
        registry.addHandler(new WebSocketHander(), "/sockjs/hello.do")
                .addInterceptors(new HandInterceptor())
                .withSockJS();
    }
	
}
