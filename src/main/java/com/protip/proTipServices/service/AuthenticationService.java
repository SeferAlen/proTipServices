package com.protip.proTipServices.service;

import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.PasswordIncorrectException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.exceptions.UserNotFoundException;
import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.model.ProTipUser;

public interface AuthenticationService {

    public abstract String loginAndGenerateToken(final Login login) throws UserNotFoundException,
                                                                           PasswordIncorrectException,
                                                                           GenericProTipServiceException,
                                                                           TokenExpiredException;
    public abstract String updateToken(final String token) throws UserNotFoundException,
                                                                  PasswordIncorrectException,
                                                                  GenericProTipServiceException,
                                                                  TokenExpiredException;
    public abstract ProTipUser getProTipUser(final String token) throws GenericProTipServiceException,
                                                                        TokenExpiredException,
                                                                        UserNotFoundException;
}
