package com.protip.proTipServices.service;

import com.protip.proTipServices.api.msgController;
import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.exceptions.UserNotFoundException;
import com.protip.proTipServices.model.MessageType;
import com.protip.proTipServices.model.ProTipUser;
import com.protip.proTipServices.model.ReceivedMessage;
import com.protip.proTipServices.model.SendMessage;
import com.protip.proTipServices.model.Message;
import com.protip.proTipServices.model.Role;
import com.protip.proTipServices.repository.MessageRepository;
import com.protip.proTipServices.repository.MessageTypeRepository;
import com.protip.proTipServices.utility.Converter;
import com.protip.proTipServices.utility.MessageReceivedStatus;
import com.protip.proTipServices.utility.ProTipValidityStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
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
    private static final MessageReceivedStatus POSTED_WITH_NEW_TOKEN = MessageReceivedStatus.POSTED_WITH_NEW_TOKEN;
    private static final MessageReceivedStatus EXPIRED = MessageReceivedStatus.EXPIRED_PRO_TIP_VALIDITY;
    private static final MessageReceivedStatus ERROR = MessageReceivedStatus.ERROR;
    private static final ProTipValidityStatus STATUS_OK = ProTipValidityStatus.OK;
    private static final ProTipValidityStatus STATUS_EXPIRED = ProTipValidityStatus.EXPIRED;
    private static final ProTipValidityStatus STATUS_NEED_UPDATE = ProTipValidityStatus.NEED_UPDATE;
    private static final Logger logger = LoggerFactory.getLogger(msgController.class);

    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageTypeRepository messageTypeRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;

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
                                                                       TokenExpiredException,
                                                                       ParseException,
                                                                       UserNotFoundException {
        Objects.requireNonNull(receivedMessage, RECEIVED_MESSAGE_NULL);
        Objects.requireNonNull(token, TOKEN_NULL);

        final Role userRole = authorizationService.getRole(token);
        final ProTipUser proTipUser = authenticationService.getProTipUser(token);
        final MessageType messageType = messageTypeRepository.findByName(receivedMessage.getMessageType());

        if (userRole.getName().equals(ROLE_ADMIN)) {
            saveMessage(receivedMessage, messageType, proTipUser);
            rabbitMQMessage(messageType);

            return POSTED;
        } else {
            final ProTipValidityStatus proTipValidityStatusFromToken = authorizationService.checkProTipUserValidity(token);

            if (proTipValidityStatusFromToken == STATUS_OK) {
                saveMessage(receivedMessage, messageType, proTipUser);
                rabbitMQMessage(messageType);

                return POSTED;
            } else if (proTipValidityStatusFromToken == STATUS_NEED_UPDATE) {
                saveMessage(receivedMessage, messageType, proTipUser);
                rabbitMQMessage(messageType);

                return POSTED_WITH_NEW_TOKEN;
            } else if (proTipValidityStatusFromToken == STATUS_EXPIRED) {
                return EXPIRED;
            }

            return ERROR;
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
     * Method for getting latest chat messages sorted by date
     *
     * @return {@link List<Message>} the List<Message> list of 30 latest messages
     */
    public List<SendMessage> getAll() {
        final List<Message> messages = messageRepository.getAllSorted();
        final List<SendMessage> sendMessages = Converter.fromMessageToSendMessage(messages);

        return sendMessages;
    }


    /**
     * Method for saving messages and notifications in db
     *
     * @param message {@link ReceivedMessage} the receivedMessage
     * @param type    {@link MessageType}     the type
     * @param type    {@link ProTipUser}      the user
     */
    private void saveMessage(final ReceivedMessage message, final MessageType type, final ProTipUser user) {
        final String loggerInfo;

        if (type.getName() == NOTIFICATION) {
            loggerInfo = "New notification: ";
        } else {
            loggerInfo = "New message: ";
        }

        logger.info(loggerInfo + message.getMessage() + ", from " + user.getFirstName() + " " + user.getLastName());
        messageRepository.save(new Message(user, message.getMessage(), new Date(), type));
    }

    /**
     * Method for handling rabbitMQ messaging
     */
    private void rabbitMQMessage (final MessageType messageType) {
        // TODO: Work in progress

        //template.convertAndSend("/topic/javainuse", message);
        rabbitTemplate.convertAndSend("proTipServicesQueueChat", messageType.getName());
    }
}
