package com.hxt.backend;

import com.hxt.backend.mapper.GroupMapper;
import com.hxt.backend.mapper.MessageMapper;
import com.hxt.backend.mapper.UserMapper;
import com.hxt.backend.service.GroupService;
import com.hxt.backend.service.MessageService;
import com.hxt.backend.service.UserService;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


@Slf4j
@Component
@ServerEndpoint("/webSocket/{userId}")
public class WebSocketServer {

    public static MessageService messageService;

    public static UserService userService;

    public static UserMapper userMapper;

    public static GroupMapper groupMapper;

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    //  私信、群聊内容加密用，勿动
    private final String headString = "$%^%^>:{!@#@))%^}";
    private final int[] move1 = {1, 2, 3};
    private final int[] move2 = {1, -2, 3, -4, 5};
    private final int[] move3 = {-1, 2, -3, 4};

    private Session session;
    private String userId;
    //  private static int onlineCount = 0;
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    private final static List<Session> sessions = Collections.synchronizedList(new ArrayList<>());


    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        log.info("尝试连接");
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
        //  log.info("[连接ID:{}] 收到消息:{}", this.userId, message);
        //  心跳机制保持在页面上长时间连接不断线
        if (message.equals("ping")) {
            try {
                this.session.getBasicRemote().sendText("pong");
            } catch (Exception e) {
                log.error("[连接ID:{}] 心跳机制失败:{}", this.userId, e.toString());
            }
            return;
        }
        JSONObject json = new JSONObject(message);
        // json字段包括发送者id，接收者id，接收团体id，内容
        int receiver_id = Integer.parseInt(json.get("receiver_id").toString());
        int group_id = Integer.parseInt(json.get("group_id").toString());
        if (group_id == -1) {
            sendPrivateMessage(message, receiver_id, Integer.parseInt(json.get("sender_id").toString()));
        }
        else {
            sendGroupMessage(message, group_id, Integer.parseInt(json.get("sender_id").toString()));
        }
    }

    public void sendPrivateMessage(String raw, Integer userId, Integer senderId) {
        //  私聊
        WebSocketServer webSocketServer = webSocketMap.get(String.valueOf(userId));
        Timestamp time = new Timestamp(System.currentTimeMillis());
        JSONObject receive = new JSONObject(raw);

        String encodeResult = encodeMessage((String) receive.get("content"));
        
        if (webSocketServer != null){
            JSONObject send = new JSONObject();
            send.put("sender_id",receive.get("sender_id"));
            send.put("content",receive.get("content"));
            send.put("group_id",-1);
            send.put("sender_avatar",userService.getUserHead(senderId));
            send.put("sender_name",userMapper.getUserNameById(senderId));
            send.put("time",time);
            log.info("【websocket消息】推送消息, message={}", send);
            try {
                webSocketServer.session.getBasicRemote().sendText(send.toString());
            } catch (Exception e) {
                log.error("[连接ID:{}] 发送消息失败, 消息:{}", this.userId, send, e);
            }
            messageService.sendPrivateMessage(senderId,userId,encodeResult,true,time);
        }
        else {
            messageService.sendPrivateMessage(senderId,userId,encodeResult,false,time);
        }
    }

    public void sendGroupMessage(String raw, Integer groupId, Integer senderId) {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        JSONObject receive = new JSONObject(raw);

        String encodeResult = encodeMessage((String) receive.get("content"));

        JSONObject send = new JSONObject();
        send.put("sender_id",receive.get("sender_id"));
        send.put("content",receive.get("content"));
        send.put("group_id",groupId);
        send.put("sender_avatar",userService.getUserHead(senderId));
        send.put("sender_name",userMapper.getUserNameById(senderId));
        send.put("time",time);
        String message = send.toString();
        messageService.sendGroupMessage(senderId,groupId,encodeResult,time);
        List<Integer> groupMember = groupMapper.selectMemberByGroupId(groupId);
        for (Integer userId: groupMember) {
            if (userId == senderId) {
                continue;
            }
            WebSocketServer webSocketServer = webSocketMap.get(String.valueOf(userId));
            if (webSocketServer != null) {
                try {
                    webSocketServer.session.getBasicRemote().sendText(message);
                    log.info("【websocket消息】推送消息, message={}", message);
                } catch (Exception e) {
                    log.error("[连接ID:{}] 发送消息失败, 消息:{}", this.userId, message, e);
                }
            }
        }

    }

    public String encodeMessage(String input) {
        Base64.Encoder encoder = Base64.getEncoder();
        StringBuilder sb = new StringBuilder();
        sb.append(headString);
        for (int i = 0; i < input.length(); i++) {
            sb.append((char)(((int)input.charAt(i)) + (move1[i % 3] * move2[i % 5] + move3[i % 4])));
        }
        return encoder.encodeToString(sb.toString().getBytes(StandardCharsets.UTF_8));
    }
}
