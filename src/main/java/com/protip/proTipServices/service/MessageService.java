package com.protip.proTipServices.service;

import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.PasswordIncorrectException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.exceptions.UserNotFoundException;
import com.protip.proTipServices.model.ReceivedMessage;
import com.protip.proTipServices.model.SendMessage;
import com.protip.proTipServices.utility.MessageReceivedStatus;

import java.text.ParseException;
import java.util.List;

public interface MessageService {
    public abstract MessageReceivedStatus newMessage(final ReceivedMessage receivedMessage,
                                                     final String token) throws UserNotFoundException,
                                                                                PasswordIncorrectException,
                                                                                GenericProTipServiceException,
                                                                                TokenExpiredException,
                                                                                ParseException;

    public abstract MessageReceivedStatus newNotification(final ReceivedMessage receivedMessage,
                                                          final String token) throws UserNotFoundException,
                                                                                     PasswordIncorrectException,
                                                                                     GenericProTipServiceException,
                                                                                     TokenExpiredException;

    public abstract List<SendMessage> getAll();
}
