package com.protip.proTipServices.service;

import com.protip.proTipServices.api.msgController;
import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.model.MessageType;
import com.protip.proTipServices.model.Message;
import com.protip.proTipServices.model.ProTipUser;
import com.protip.proTipServices.model.ReceivedMessage;
import com.protip.proTipServices.model.Role;
import com.protip.proTipServices.repository.MessageRepository;
import com.protip.proTipServices.repository.MessageTypeRepository;
import com.protip.proTipServices.utility.JwtTokenUtil;
import com.protip.proTipServices.utility.MessageReceivedStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Service for user messages and message related actions
 */
@Service
public class MessageServiceImpl implements MessageService {
    private static final String RECEIVED_MESSAGE_NULL = "Received message must not be null";
    private static final String TOKEN_NULL = "Token must not be null";
    private static final String MESSAGE = "MESSAGE";
    private static final String NOTIFICATION = "NOTIFICATION";
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";
    private static final MessageReceivedStatus POSTED = MessageReceivedStatus.POSTED;
    private static final MessageReceivedStatus EXPIRED = MessageReceivedStatus.EXPIRED_PRO_TIP_VALIDITY;
    private static final MessageReceivedStatus ERROR = MessageReceivedStatus.ERROR;
    private static final Logger logger = LoggerFactory.getLogger(msgController.class);

    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageTypeRepository messageTypeRepository;

    /**
     * Method for handling new message being posted
     *
     * @param receivedMessage {@link ReceivedMessage} the receivedMessage
     * @param token           {@link String}          the token
     * @return {@link MessageReceivedStatus}          the status representing message posting result
     * @throws GenericProTipServiceException the generic proTipService exception
     * @throws TokenExpiredException         the token expired exception
     */
    public MessageReceivedStatus newMessage(final ReceivedMessage receivedMessage,
                                            final String token) throws GenericProTipServiceException,
                                                                       TokenExpiredException {
        Objects.requireNonNull(receivedMessage, RECEIVED_MESSAGE_NULL);
        Objects.requireNonNull(token, TOKEN_NULL);

        // TODO: Work in progress

        final Role userRole = authorizationService.getRole(token);
        final ProTipUser proTipUser = authenticationService.getProTipUser(token);
        final MessageType messageType = messageTypeRepository.findByName(receivedMessage.getMessageType());

        if(userRole.getName().equals(ROLE_ADMIN) && receivedMessage.getMessageType().equals(NOTIFICATION)) {
            checkproTipUserValidityDate(token);
            messageRepository.save(new Message(proTipUser, receivedMessage.getMessage(), messageType));

            return POSTED;
        } else {
            authorizationService.checkProTipUserValidity(token);
            //template.convertAndSend("/topic/javainuse", message);
            //rabbitTemplate.convertAndSend("proTipServicesQueueChat", message.getMessage());
            logger.info("New message : " + ", from " );

            return POSTED;
        }
    }

    /**
     * Method for handling new notification being posted
     *
     * @param receivedMessage {@link ReceivedMessage} the receivedMessage
     * @param token           {@link String}          the token
     * @return {@link MessageReceivedStatus}          the status representing notification posting result
     * @throws GenericProTipServiceException the generic proTipService exception
     * @throws TokenExpiredException         the token expired exception
     */
    public MessageReceivedStatus newNotification(final ReceivedMessage receivedMessage,
                                                 final String token) throws GenericProTipServiceException,
                                                                            TokenExpiredException {
        Objects.requireNonNull(receivedMessage, RECEIVED_MESSAGE_NULL);
        Objects.requireNonNull(token, TOKEN_NULL);

        // TODO: Work in progress

        return ERROR;
    }

    /**
     * Check validity date sent by user trying to register in order to prevent date manipulation at frontend
     *
     * @param token {@link String} the token sent from user
     * @return {@link boolean}     representing validity status
     */
    private boolean checkproTipUserValidityDate(final String token) {
        return JwtTokenUtil.checkProTipUserValidityDate(token);
    }
}
