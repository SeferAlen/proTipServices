package com.protip.proTipServices.api;

import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.PasswordIncorrectException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.exceptions.UserNotFoundException;
import com.protip.proTipServices.model.GetMessages;
import com.protip.proTipServices.model.ReceivedMessage;
import com.protip.proTipServices.model.GetMessagesResponse;
import com.protip.proTipServices.service.AuthenticationService;
import com.protip.proTipServices.service.AuthorizationService;
import com.protip.proTipServices.service.MessageService;
import com.protip.proTipServices.utility.ProTipUserActionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.ParseException;

/**
 * REST controller for handling messages between clients and storing them into db
 */
@RestController
@RequestMapping(value = "message")
public class msgController extends basicController {
    private static final String MESSAGE_POSTED = "Posted";
    private static final String PRO_TIP_USER_EXPIRED = "ProTipUser status expired";
    private static final String PRO_TIP_SERVICES = "ProTipServices";
    private static final String TOKEN = "New token: ";
    private static final ProTipUserActionStatus OK = ProTipUserActionStatus.OK;
    private static final ProTipUserActionStatus OK_WITH_NEW_TOKEN = ProTipUserActionStatus.OK_WITH_NEW_TOKEN;
    private static final ProTipUserActionStatus EXPIRED = ProTipUserActionStatus.EXPIRED_PRO_TIP_VALIDITY;
    private static final ProTipUserActionStatus ERROR = ProTipUserActionStatus.ERROR;

    @Autowired
    private MessageService messageService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private AuthorizationService authorizationService;

    /**
     * Receive message endpoint for receiving messages
     *
     * @param auth    {@link String} the auth header containing token
     * @param message {@link String} the message
     * @return {@link ResponseEntity} the response entity with body containing message and Http status
     * @throws GenericProTipServiceException the generic proTipService exception
     * @throws TokenExpiredException         the token expired exception
     * @throws UserNotFoundException         the user not found exception
     * @throws PasswordIncorrectException    the token expired exception
     */
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> receivedMessage(@RequestHeader("Authorization") final String auth,
                                             @Valid @RequestBody final ReceivedMessage message) throws UserNotFoundException,
                                                                                                PasswordIncorrectException,
                                                                                                GenericProTipServiceException,
                                                                                                TokenExpiredException,
                                                                                                ParseException {
        final String token = auth.substring(auth.indexOf(EMPTY_SPACE));
        final ProTipUserActionStatus proTipUserActionStatus = messageService.newMessage(message, token);

        if (proTipUserActionStatus == OK) {
            return response(MESSAGE_POSTED, HTTP_CREATED);
        } else if (proTipUserActionStatus == OK_WITH_NEW_TOKEN) {
            return response(MESSAGE_POSTED + EMPTY_SPACE + TOKEN + authenticationService.updateToken(token), HTTP_ACCEPTED);
        } else if (proTipUserActionStatus == EXPIRED) {
            return response(PRO_TIP_USER_EXPIRED, HTTP_BAD_REQUEST);
        } else {
            return response(SERVICE_ERROR_MESSAGE + ". " + SERVICE_ERROR_DETAILS, HTTP_INTERNAL_ERROR);
        }
    }

    /**
     * Get latest chat messages endpoint
     *
     * @return {@link ResponseEntity} the response entity with body containing messages and Http status
     * @throws GenericProTipServiceException the generic proTipService exception
     * @throws TokenExpiredException      the token expired exception
     * @throws UserNotFoundException      the user not found exception
     * @throws PasswordIncorrectException the password incorrect exception
     */
    @GetMapping(value = "/messages", produces = "application/json")
    public ResponseEntity<?> getAllMessages(@RequestHeader("Authorization") final String auth) throws TokenExpiredException,
                                                                                                      GenericProTipServiceException,
                                                                                                      UserNotFoundException,
                                                                                                      ParseException,
                                                                                                      PasswordIncorrectException {
        final String token = auth.substring(auth.indexOf(EMPTY_SPACE));
        final GetMessages messages = messageService.getAll(token);
        final ProTipUserActionStatus proTipUserActionStatus = messages.getUserStatus();
        final GetMessagesResponse getMessagesResponse = new GetMessagesResponse();
        String updatedToken = EMPTY_STRING;
        getMessagesResponse.setToken(updatedToken);

        if (proTipUserActionStatus == OK) {
            getMessagesResponse.setMessages(messages.getMessages());
            return response(getMessagesResponse, HTTP_OK);
        } else if (proTipUserActionStatus == OK_WITH_NEW_TOKEN) {
            updatedToken = authenticationService.updateToken(token);
            getMessagesResponse.setMessages(messages.getMessages());
            getMessagesResponse.setToken(updatedToken);
            return response(getMessagesResponse, HTTP_ACCEPTED);
        } else if (proTipUserActionStatus == EXPIRED) {
            return response(PRO_TIP_USER_EXPIRED, HTTP_BAD_REQUEST);
        } else {
            return response(SERVICE_ERROR_MESSAGE + ". " + SERVICE_ERROR_DETAILS, HTTP_INTERNAL_ERROR);
        }
    }
}
