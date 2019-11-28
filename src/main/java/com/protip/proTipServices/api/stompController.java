package com.protip.proTipServices.api;

import com.protip.proTipServices.model.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * REST controller for handling stomp client messages
 */
@Controller
public class stompController extends basicController {

    // TODO: Currently rabbitMQ messaging is not being used

//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    /**
//     * Receive message endpoint for receiving messages from stomp client
//     *
//     * @param message {@link Message} the message
//     * @return {@link ResponseEntity} the response entity with body containing token and Http status
//     */
//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/javainuse")
//    public ResponseEntity<?> sendMessage(@Payload final Message message) {
//        return response(message, HTTP_OK);
//    }
//
//    /**
//     * Receive message endpoint for receiving messages from stomp client
//     *
//     * @param message        {@link Message}                   the message
//     * @param headerAccessor {@link SimpMessageHeaderAccessor} the headerAccessor
//     * @return {@link ResponseEntity} the response entity with body containing token and Http status
//     */
//    @MessageMapping("/chat.newUser")
//    @SendTo("/topic/javainuse")
//    public ResponseEntity<?> newUser(@Payload final Message message, final SimpMessageHeaderAccessor headerAccessor) {
//
//        //message.setMessageType(MessageType.MESSAGE);
//        //rabbitTemplate.convertAndSend("proTipServicesQueueChat", message.getMessage());
//        //headerAccessor.getSessionAttributes().put("username", message);
//        return response(message, HTTP_OK);
//    }
}
