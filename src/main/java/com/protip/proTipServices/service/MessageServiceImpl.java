package com.protip.proTipServices.service;

import com.protip.proTipServices.api.msgController;
import com.protip.proTipServices.config.Config;
import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.exceptions.UserNotFoundException;
import com.protip.proTipServices.model.*;
import com.protip.proTipServices.repository.MessageRepository;
import com.protip.proTipServices.repository.MessageTypeRepository;
import com.protip.proTipServices.utility.Converter;
import com.protip.proTipServices.utility.ProTipUserActionStatus;
import com.protip.proTipServices.utility.ProTipValidityStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Service for user messages and message related actions
 */
@Service
public class MessageServiceImpl implements MessageService {
    private static final String RECEIVED_MESSAGE_NULL = "Received message must not be null";
    private static final String MESSAGE_TYPE_NULL = "Message type must not be null";
    private static final String USER_NULL = "User must not be null";
    private static final String ROLE_NULL = "Role must not be null";
    private static final String TOKEN_NULL = "Token must not be null";
    private static final String NEW_MESSAGE = "New notification: ";
    private static final String NEW_NOTIFICATION = "New message: ";
    private static final String MESSAGE = "MESSAGE";
    private static final String NOTIFICATION = "NOTIFICATION";
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";
    private static final ProTipUserActionStatus ADMIN = ProTipUserActionStatus.ADMIN;
    private static final ProTipUserActionStatus OK = ProTipUserActionStatus.OK;
    private static final ProTipUserActionStatus OK_WITH_NEW_TOKEN = ProTipUserActionStatus.OK_WITH_NEW_TOKEN;
    private static final ProTipUserActionStatus EXPIRED = ProTipUserActionStatus.EXPIRED_PRO_TIP_VALIDITY;
    private static final ProTipUserActionStatus ERROR = ProTipUserActionStatus.ERROR;
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
    private RabbitTemplate rabbitTemplate;

    /**
     * Method for handling new message being posted
     *
     * @param receivedMessage {@link ReceivedMessage} the receivedMessage
     * @param token           {@link String}          the token
     * @return {@link ProTipUserActionStatus}         the status representing message posting result
     * @throws GenericProTipServiceException the generic proTipService exception
     * @throws TokenExpiredException         the token expired exception
     */
    public ProTipUserActionStatus newMessage(final ReceivedMessage receivedMessage,
                                             final String token) throws GenericProTipServiceException,
                                                                       TokenExpiredException,
                                                                       ParseException,
                                                                       UserNotFoundException {
        Objects.requireNonNull(receivedMessage, RECEIVED_MESSAGE_NULL);
        Objects.requireNonNull(token, TOKEN_NULL);

        final Role userRole = authorizationService.getRole(token);
        final ProTipUser proTipUser = authenticationService.getProTipUser(token);
        final MessageType messageType = messageTypeRepository.findByName(receivedMessage.getMessageType());

        Objects.requireNonNull(userRole, ROLE_NULL);
        Objects.requireNonNull(proTipUser, USER_NULL);
        Objects.requireNonNull(messageType, MESSAGE_TYPE_NULL);

        final ProTipUserActionStatus userStatus = verifyUser(token, userRole);

        if (userStatus == ADMIN) {
            saveMessage(receivedMessage, messageType, proTipUser);
            if (messageType.getName() == NOTIFICATION) {
                rabbitMQNotification(proTipUser.getFirstName() + " " + proTipUser.getLastName() + ": " + receivedMessage.getMessage());
            } else {
                rabbitMQMessage(proTipUser.getFirstName() + " " + proTipUser.getLastName() + ": " + receivedMessage.getMessage());
            }
            return OK;
        } else {
            if (userStatus == OK) {
                saveMessage(receivedMessage, messageType, proTipUser);
                rabbitMQMessage(proTipUser.getFirstName() + " " + proTipUser.getLastName() + ": " + receivedMessage.getMessage());

                return OK;
            } else if (userStatus == OK_WITH_NEW_TOKEN) {
                saveMessage(receivedMessage, messageType, proTipUser);
                rabbitMQMessage(proTipUser.getFirstName() + " " + proTipUser.getLastName() + ": " + receivedMessage.getMessage());

                return OK_WITH_NEW_TOKEN;
            } else if (userStatus == EXPIRED) {
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
     * @return {@link ProTipUserActionStatus} the status representing notification posting result
     * @throws GenericProTipServiceException the generic proTipService exception
     * @throws TokenExpiredException         the token expired exception
     */
    public ProTipUserActionStatus newNotification(final ReceivedMessage receivedMessage,
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
    public GetMessages getAll(final String token) throws GenericProTipServiceException,
                                                         TokenExpiredException,
                                                         ParseException,
                                                         UserNotFoundException {
        Objects.requireNonNull(token, TOKEN_NULL);

        final Role userRole = authorizationService.getRole(token);
        final ProTipUser proTipUser = authenticationService.getProTipUser(token);

        Objects.requireNonNull(userRole, ROLE_NULL);
        Objects.requireNonNull(proTipUser, USER_NULL);

        final ProTipUserActionStatus userStatus = verifyUser(token, userRole);

        if (userStatus == ADMIN || userStatus == OK) {
            final List<SendMessage> messages = Converter.fromMessageToSendMessage(messageRepository.getAllSorted());

            return new GetMessages(messages, OK);
        } else if (userStatus == OK_WITH_NEW_TOKEN) {
            final List<SendMessage> messages = Converter.fromMessageToSendMessage(messageRepository.getAllSorted());

            return new GetMessages(messages, OK_WITH_NEW_TOKEN);
        } else if (userStatus == EXPIRED) {
            return new GetMessages(new ArrayList<>(), EXPIRED);
        } else return new GetMessages(new ArrayList<>(), ERROR);
    }

    /**
     * Method for checking user status in order to perform ProTipUser related actions
     *
     * @param token {@link String}  the token
     * @param userRole {@link Role} the token
     * @return {@link ProTipUserActionStatus} the status representing ProTipUser related actions
     * @throws GenericProTipServiceException the generic proTipService exception
     * @throws TokenExpiredException         the token expired exception
     */
    private ProTipUserActionStatus verifyUser(final String token,
                                              final Role userRole) throws GenericProTipServiceException,
                                                                                  TokenExpiredException,
                                                                                  ParseException {

        if (userRole.getName().equals(ROLE_ADMIN)) {
            return ADMIN;
        } else {
            final ProTipValidityStatus proTipValidityStatusFromToken = authorizationService.checkProTipUserValidity(token);

            if (proTipValidityStatusFromToken == STATUS_OK) {
                return OK;
            } else if (proTipValidityStatusFromToken == STATUS_NEED_UPDATE) {
                return OK_WITH_NEW_TOKEN;
            } else if (proTipValidityStatusFromToken == STATUS_EXPIRED) {
                return EXPIRED;
            }
            return ERROR;
        }
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
            loggerInfo = NEW_NOTIFICATION;
        } else {
            loggerInfo = NEW_MESSAGE;
        }

        logger.info(loggerInfo + message.getMessage() + ", from " + user.getFirstName() + " " + user.getLastName());
        messageRepository.save(new Message(user, message.getMessage(), new Date(), type));
    }

    /**
     * Method for handling rabbitMQ messaging
     */
    private void rabbitMQMessage(final String message) {
        // TODO: Work in progress

        //template.convertAndSend("/topic/javainuse", message);
        rabbitTemplate.convertAndSend(Config.getChatQueue(), message);
    }

    /**
     * Method for handling rabbitMQ notifications
     */
    private void rabbitMQNotification(final String message) {
        // TODO: Work in progress

        //template.convertAndSend("/topic/javainuse", message);
        rabbitTemplate.convertAndSend(Config.getNotificationQueueQueue(), message);
    }
}
