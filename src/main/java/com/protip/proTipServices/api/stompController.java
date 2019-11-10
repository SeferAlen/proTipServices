package com.protip.proTipServices.api;

import com.protip.proTipServices.model.Message;
import com.protip.proTipServices.utility.MessageType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class stompController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/javainuse")
    public ResponseEntity<?> sendMessage(@Payload Message message) {
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @MessageMapping("/chat.newUser")
    @SendTo("/topic/javainuse")
    public ResponseEntity<?> newUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        //message.setMessageType(MessageType.MESSAGE);
        //rabbitTemplate.convertAndSend("proTipServicesQueueChat", message.getMessage());
        //headerAccessor.getSessionAttributes().put("username", message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
