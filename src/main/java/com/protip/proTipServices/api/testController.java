package com.protip.proTipServices.api;

import com.protip.proTipServices.config.Config;
import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.model.Message;
import com.protip.proTipServices.model.TokenSet;
import com.protip.proTipServices.repository.MessageTypeRepository;
import com.protip.proTipServices.service.AuthenticationService;
import com.protip.proTipServices.service.AuthorizationService;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * REST controller for various testing
 */
@RestController
@RequestMapping(value = "/test")
public class testController extends basicController {
    private static final String TEST_SENDER = "Alen";
    private static final String MESSAGE = "MESSAGE";

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    MessageTypeRepository messageTypeRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Test user authorization endpoint
     *
     * @param tokenSet the token set
     * @return         the response entity
     */
    @GetMapping(value = "authUser")
    public ResponseEntity<?> testUserAuthorization(@RequestBody final TokenSet tokenSet) throws GenericProTipServiceException,
                                                                                                TokenExpiredException {

        final Boolean userOk = authorizationService.authorizeUser(tokenSet.getToken());
        final String message;

        if (userOk) {
            message = USER_AUTHORIZED;
        } else {
            message = USER_UNAUTHORIZED;
        }

        return response(message, HTTP_OK);
    }

    /**
     * Test admin authorization endpoint
     *
     * @param tokenSet the token set
     * @return         the response entity
     */
    @GetMapping(value = "authAdmin")
    public ResponseEntity<?> testAdminAuthorization(@RequestBody final TokenSet tokenSet) throws GenericProTipServiceException,
                                                                                                 TokenExpiredException {
        final Boolean userOk = authorizationService.authorizeAdmin(tokenSet.getToken());
        final String message;

        if (userOk) {
            message = USER_AUTHORIZED;
        } else {
            message = USER_UNAUTHORIZED;
        }

        return response(message, HTTP_OK);
    }

    /**
     * Test RabbitMQ message sending endpoint
     *
     * @param message the message
     * @return        the response entity
     */
    @PostMapping(value = "sendMessage")
    public ResponseEntity<?> testAdminAuthorization(@RequestBody final Message message) {

        if(message.getSender().equals(TEST_SENDER)) {
            rabbitTemplate.convertAndSend(Config.getNotificationQueueQueue(), message.getMessage());
        } else {
            rabbitTemplate.convertAndSend(Config.getChatQueue(), message.getMessage());
        }

        return response(message, HTTP_OK);
    }

    /**
     * Test Caching
     *
     * @return        the response entity
     */
    @GetMapping(value = "testCaching")
    public ResponseEntity<?> testCaching() {

        Instant timeBefore = new Instant();
        messageTypeRepository.findByName(MESSAGE);
        Interval interval = new Interval(timeBefore, new Instant());

        return response("Time passed " + interval.toString(), HTTP_OK);
    }
}
