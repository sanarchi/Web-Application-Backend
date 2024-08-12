package com.message.messaging.config;

import com.message.messaging.entity.Message;

import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.message.messaging.entity.MessageType;

import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lombok.Builder;
@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {
	
//	 private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
	private  SimpMessageSendingOperations messagingTemplate ;
	
	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event)
	{
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String username = null;
		
		if(headerAccessor.getSessionAttributes() != null)
		{
			username = (String) headerAccessor.getSessionAttributes().get("username");
		}
		
		if(username != null)
		{
			//logger.info("user disconnected: {}", username);
			Message chatMessage = new Message();
            chatMessage.setType(MessageType.DISCONNECT);
            chatMessage.setSender(username);
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
		}
	}
	
}
