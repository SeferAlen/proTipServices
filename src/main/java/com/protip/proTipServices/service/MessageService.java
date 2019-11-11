package com.protip.proTipServices.service;

import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.PasswordIncorrectException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.exceptions.UserNotFoundException;
import com.protip.proTipServices.model.ReceivedMessage;
import com.protip.proTipServices.utility.MessageReceivedStatus;

public interface MessageService {
    MessageReceivedStatus newMessage(final ReceivedMessage receivedMessage,
                                     final String token) throws UserNotFoundException,
                                                                PasswordIncorrectException,
                                                                GenericProTipServiceException,
                                                                TokenExpiredException;

    MessageReceivedStatus newNotification(final ReceivedMessage receivedMessage,
                                          final String token) throws UserNotFoundException,
                                                                     PasswordIncorrectException,
                                                                     GenericProTipServiceException,
                                                                     TokenExpiredException;
}
