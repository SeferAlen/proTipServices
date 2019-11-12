package com.protip.proTipServices.api;

import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.PasswordIncorrectException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.exceptions.UserNotFoundException;
import com.protip.proTipServices.model.Message;
import com.protip.proTipServices.model.ReceivedMessage;
import com.protip.proTipServices.service.AuthenticationService;
import com.protip.proTipServices.service.MessageService;
import com.protip.proTipServices.utility.MessageReceivedStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;
import java.text.ParseException;

/**
 * REST controller for handling messages between clients and storing them into db
 */
@RestController
@RequestMapping(value = "message")
public class msgController extends basicController {
    private static final String MESSAGE_POSTED = "Posted";
    private static final String MESSAGE_POSTED_NEW_TOKEN = "Posted, new token: ";
    private static final String PRO_TIP_USER_EXPIRED = "ProTipUser status expired";
    private static final MessageReceivedStatus POSTED = MessageReceivedStatus.POSTED;
    private static final MessageReceivedStatus POSTED_WITH_NEW_TOKEN = MessageReceivedStatus.POSTED_WITH_NEW_TOKEN;
    private static final MessageReceivedStatus EXPIRED = MessageReceivedStatus.EXPIRED_PRO_TIP_VALIDITY;
    private static final MessageReceivedStatus ERROR = MessageReceivedStatus.ERROR;

    @Autowired
    private MessageService messageService;
    @Autowired
    private AuthenticationService authenticationService;

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
                                             @Valid @RequestBody final ReceivedMessage message) throws UserNotFoundException,
                                                                                                PasswordIncorrectException,
                                                                                                GenericProTipServiceException,
                                                                                                TokenExpiredException,
                                                                                                ParseException {
        final String token = auth.substring(auth.indexOf(EMPTY_SPACE));
        final MessageReceivedStatus messageReceivedStatus = messageService.newMessage(message, token);

        if (messageReceivedStatus == POSTED) {
            return new ResponseEntity<>(MESSAGE_POSTED, HTTP_CREATED);
        } else if (messageReceivedStatus == POSTED_WITH_NEW_TOKEN) {
            return new ResponseEntity<>(MESSAGE_POSTED_NEW_TOKEN + authenticationService.updateToken(token),
                    HTTP_CREATED);
        } else if (messageReceivedStatus == EXPIRED) {
            return new ResponseEntity<>(PRO_TIP_USER_EXPIRED, HTTP_BAD_REQUEST);
        } else {
            return new ResponseEntity<>(SERVICE_ERROR_MESSAGE + ". " + SERVICE_ERROR_DETAILS, HTTP_INTERNAL_ERROR);
        }
    }
}
