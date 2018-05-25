package com.hj.webSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author Administrator
 * 消息处理中心
 * 相当于使用原始J2EE API实现中的@OnMessage和@OnClose注解功能
 */
public class WebSocketHander implements WebSocketHandler{

	private static final List<WebSocketSession> users = new ArrayList<WebSocketSession>();
	
	//用户标识
    private static final String CLIENT_ID = "userId";
    
    /**
     * 初次链接成功执行
     * 记录用户的连接标识，便于后面发信息，这里我是记录将id记录在Map集合中。
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("链接成功......");
        users.add(session);
        String userId = getClientId(session);
        if (userId != null) {
            //查询未读消息
            int count = 5;
            session.sendMessage(new TextMessage(count + ""));
        }
    }
    /**
     * 接受消息处理消息
     * 可以对H5 Websocket的send方法进行处理
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        sendMessageToUsers(new TextMessage(message.getPayload() + ""));
    }
    
    /*
	*连接出错处理，主要是关闭出错会话的连接，和删除在Map集合中的记录
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("链接出错，关闭链接......");
        users.remove(session);
    }
    
    /*
     * 连接已关闭，移除在Map集合中的记录。
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("链接关闭......" + status.toString());
        users.remove(session);
    }
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 给某个用户发送消息
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get("WEBSOCKET_USERNAME").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    
    /**
     * 获取用户标识
     * @param session
     * @return
     */
    private String getClientId(WebSocketSession session) {
        try {
            String clientId = (String) session.getAttributes().get(CLIENT_ID);
            return clientId;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static List<WebSocketSession>  getCurrentUsers(){
    	return users;
    }

}
