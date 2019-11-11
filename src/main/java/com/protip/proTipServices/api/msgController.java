package com.protip.proTipServices.api;

import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.PasswordIncorrectException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.exceptions.UserNotFoundException;
import com.protip.proTipServices.model.ReceivedMessage;
import com.protip.proTipServices.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * REST controller for handling messages between clients and storing them into db
 */
@RestController
@RequestMapping(value = "message")
public class msgController extends basicController {

    @Autowired
    private MessageService messageService;

    /**
     * Receive message endpoint for receiving messages
     *
     * @param auth    {@link String}    the auth header containing token
     * @param message {@link String}    the message
     * @return {@link ResponseEntity}   the response entity with body containing message and Http status
     * @throws GenericProTipServiceException the generic proTipService exception
     * @throws TokenExpiredException         the token expired exception
     * @throws UserNotFoundException         the user not found exception
     * @throws PasswordIncorrectException    the token expired exception
     */
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> receivedMessage(@RequestHeader("Authorization") final String auth,
                                             @RequestBody final ReceivedMessage message) throws UserNotFoundException,
                                                                                                PasswordIncorrectException,
                                                                                                GenericProTipServiceException,
                                                                                                TokenExpiredException {
        final String token = auth.substring(auth.indexOf(EMPTY_SPACE));

        messageService.newMessage(message, token);
        return new ResponseEntity<>(message, HTTP_OK);
    }
}
