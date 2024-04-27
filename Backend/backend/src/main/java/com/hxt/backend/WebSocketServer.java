package com.hxt.backend;

import com.hxt.backend.mapper.MessageMapper;
import jakarta.annotation.Resource;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
@ServerEndpoint("/webSocket/{userId}")
public class WebSocketServer {
    @Resource
    private MessageMapper messageMapper;

    private Session session;
    private String userId;
    //  private static int onlineCount = 0;
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    private final static List<Session> sessions = Collections.synchronizedList(new ArrayList<>());

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        webSocketSet.add(this);
        sessions.add(session);
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            webSocketMap.put(userId,this);
        } else {
            webSocketMap.put(userId,this);
        }
        log.info("[连接ID:{}] 建立连接, 当前连接数:{}", this.userId, webSocketMap.size());
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        webSocketMap.remove(userId);
        log.info("[连接ID:{}] 断开连接, 当前连接数:{}", userId, webSocketMap.size());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.info("[连接ID:{}] 错误原因:{}", this.userId, error.getMessage());
        error.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("[连接ID:{}] 收到消息:{}", this.userId, message);
    }

    public void sendPrivateMessage(String message, Integer userId) {
        //  私聊
        WebSocketServer webSocketServer = webSocketMap.get(String.valueOf(userId));
        if (webSocketServer != null){
            log.info("【websocket消息】推送消息, message={}", message);
            try {
                webSocketServer.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                log.error("[连接ID:{}] 发送消息失败, 消息:{}", this.userId, message, e);
            }
        }
    }

    public void sendGroupMessage(String message) {
        //  现状为全局发消息，可以改造为向群聊内所有人发消息
        try {
            for (Session session : sessions) {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(message);
                    log.info("[连接ID:{}] 发送消息:{}",session.getRequestParameterMap().get("userId"), message);
                }
            }
        } catch (Exception e) {
            log.error("[连接ID:{}] 发送消息失败, 消息:{}", this.userId, message, e);
        }
    }
}
