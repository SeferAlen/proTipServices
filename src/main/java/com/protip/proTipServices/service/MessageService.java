package com.protip.proTipServices.service;

import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.PasswordIncorrectException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.exceptions.UserNotFoundException;
import com.protip.proTipServices.model.GetMessages;
import com.protip.proTipServices.model.ReceivedMessage;
import com.protip.proTipServices.model.SendMessage;
import com.protip.proTipServices.utility.ProTipUserActionStatus;

import java.text.ParseException;
import java.util.List;

public interface MessageService {
    public abstract ProTipUserActionStatus newMessage(final ReceivedMessage receivedMessage,
                                                      final String token) throws UserNotFoundException,
                                                                                PasswordIncorrectException,
                                                                                GenericProTipServiceException,
                                                                                TokenExpiredException,
                                                                                ParseException;

    public abstract ProTipUserActionStatus newNotification(final ReceivedMessage receivedMessage,
                                                           final String token) throws UserNotFoundException,
                                                                                     PasswordIncorrectException,
                                                                                     GenericProTipServiceException,
                                                                                     TokenExpiredException;

    public abstract GetMessages getAll(final String token) throws GenericProTipServiceException,
                                                                  TokenExpiredException,
                                                                  ParseException,
                                                                  UserNotFoundException;
}
