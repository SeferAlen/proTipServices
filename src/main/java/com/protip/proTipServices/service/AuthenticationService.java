package com.protip.proTipServices.service;

import com.protip.proTipServices.exceptions.PasswordIncorrectException;
import com.protip.proTipServices.exceptions.UserNotFoundException;
import com.protip.proTipServices.model.Login;

public interface AuthenticationService {

    String loginAndGenerateToken(final Login login) throws UserNotFoundException, PasswordIncorrectException;
}
