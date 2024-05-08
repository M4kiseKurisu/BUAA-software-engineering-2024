package com.hxt.backend;

import com.hxt.backend.mapper.GroupMapper;
import com.hxt.backend.mapper.MessageMapper;
import com.hxt.backend.mapper.UserMapper;
import com.hxt.backend.service.MessageService;
import com.hxt.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableWebSocket
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Autowired
    public void setService(UserService userService, UserMapper userMapper, MessageService messageService, GroupMapper groupMapper) {
        WebSocketServer.messageService = messageService;
        WebSocketServer.userService = userService;
        WebSocketServer.userMapper = userMapper;
        WebSocketServer.groupMapper = groupMapper;
    }
}
