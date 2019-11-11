package com.protip.proTipServices.listeners;

import com.protip.proTipServices.api.msgController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * Listener for web socket related events
 */
@Component
public class WebSocketEventListener {
    private static final String NEW_CONNECTION = "Received a new web socket connection";
    private static final String USERNAME = "username";
    private final Logger logger = LoggerFactory.getLogger(msgController.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    /**
     * Method for handling new web socket connection
     *
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info(NEW_CONNECTION);
    }

    /**
     * Method for handling web socket disconnect
     *
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        final String username = (String) headerAccessor.getSessionAttributes().get(USERNAME);

        if(username != null) {
            //Message chatMessage = new Message();
            //chatMessage.setMessageType(MessageType.MESSAGE);
            //chatMessage.setSender(username);
            //messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
