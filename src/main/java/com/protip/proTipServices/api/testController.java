package com.protip.proTipServices.api;

import com.protip.proTipServices.config.Config;
import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.model.Message;
import com.protip.proTipServices.model.TokenSet;
import com.protip.proTipServices.service.AuthenticationService;
import com.protip.proTipServices.service.AuthorizationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for various testing
 */
@RestController
@RequestMapping(value = "/test")
public class testController extends basicController {
    private static final String USER_AUTHORIZED = "User is authorized";
    private static final String USER_UNAUTHORIZED = "User not authorized, cannot proceed";
    private static final String TEST_SENDER = "Alen";

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * Test user authorization
     *
     * @param tokenSet the token set
     * @return the response entity
     */
    @GetMapping(value = "authUser")
    public ResponseEntity<?> testUserAuthorization(@RequestBody final TokenSet tokenSet) throws GenericProTipServiceException, TokenExpiredException {

        final Boolean userOk = authorizationService.authorizeUser(tokenSet.getToken());
        final String message;

        if (userOk) {
            message = USER_AUTHORIZED;
        } else {
            message = USER_UNAUTHORIZED;
        }

        return new ResponseEntity<>(message, HTTP_OK);
    }

    /**
     * Test admin authorization
     *
     * @param tokenSet the token set
     * @return the response entity
     */
    @GetMapping(value = "authAdmin")
    public ResponseEntity<?> testAdminAuthorization(@RequestBody final TokenSet tokenSet) throws GenericProTipServiceException, TokenExpiredException {
        final Boolean userOk = authorizationService.authorizeAdmin(tokenSet.getToken());

        final String message;
        if(userOk) {
            message = USER_AUTHORIZED;
        } else {
            message = USER_UNAUTHORIZED;
        }
        return new ResponseEntity<>(message, HTTP_OK);
    }

    /**
     * Test RabbitMQ message sending
     *
     * @param message the message
     * @return the response entity
     */
    @PostMapping(value = "sendMessage")
    public ResponseEntity<?> testAdminAuthorization(@RequestBody final Message message) {

        if(message.getSender().equals(TEST_SENDER)) {
            rabbitTemplate.convertAndSend(Config.getNotificationQueueQueue(), message.getMessage());
        } else {
            rabbitTemplate.convertAndSend(Config.getChatQueue(), message.getMessage());
        }
        return new ResponseEntity<>(message, HTTP_OK);
    }
}
