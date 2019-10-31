package com.protip.proTipServices.api;

import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.model.Message;
import com.protip.proTipServices.model.Role;
import com.protip.proTipServices.service.AuthorizationService;
import com.protip.proTipServices.utility.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * REST controller for handling messages between clients
 */
@RestController
@RequestMapping(value = "message")
public class msgController {
    private final Logger logger = LoggerFactory.getLogger(msgController.class);

    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    SimpMessagingTemplate template;

    /**
     * Received message response entity.
     *
     * @param auth    the auth header containing token
     * @param message the message
     * @return the response entity with body containing message and Http status
     * @throws GenericProTipServiceException the generic proTipService exception
     * @throws TokenExpiredException         the token expired exception
     */
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> receivedMessage(@RequestHeader("Authorization") final String auth,
                                             @RequestBody final Message message) throws GenericProTipServiceException,
                                                                                        TokenExpiredException {
        final String token = auth.substring(auth.indexOf(" "));
        final Role userRole = authorizationService.getRole(token);

        if(userRole.getName().equals("ADMIN") && message.getMessageType() == MessageType.NOTIFICATION) {
            logger.info("New notification : " + message.getMessage() + ", from " + message.getSender());
        } else {
            message.setMessageType(MessageType.MESSAGE);
            template.convertAndSend("/topic/javainuse", message);
            rabbitTemplate.convertAndSend("proTipServicesQueueChat", message.getMessage());
            logger.info("New message : " + message.getMessage() + ", from " + message.getSender());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
