package com.message.messaging.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.message.messaging.broker.Sender;
import com.message.messaging.entity.Message;

@Controller
public class MessageController {

	private Sender sender;
	private SimpMessageSendingOperations messagingTemplate;
	

    public MessageController(Sender sender, SimpMessageSendingOperations messagingTemplate) {
        this.sender = sender;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.send-message")
    public void sendMessage(@Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        chatMessage.setSessionId(headerAccessor.getSessionId());
        sender.send("messaging", chatMessage);
        //logger.info("Sending message to /topic/public: " + chatMessage);
        messagingTemplate.convertAndSend("/topic/public", chatMessage);
        //logger.info("Message sent to /topic/public: " + chatMessage);
    }

    @MessageMapping("/chat.add-user")
    @SendTo("/topic/public")
    public Message addUser(
            @Payload Message chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        if (headerAccessor.getSessionAttributes() != null) {
            headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        }

        return chatMessage;
    }
}
