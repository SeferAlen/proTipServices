package com.protip.proTipServices.service;

import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.model.Role;
import com.protip.proTipServices.utility.ProTipValidityStatus;

import java.text.ParseException;

public interface AuthorizationService {

    boolean authorizeUser(final String token) throws GenericProTipServiceException, TokenExpiredException;
    boolean authorizeAdmin(final String token) throws GenericProTipServiceException, TokenExpiredException;
    public ProTipValidityStatus checkProTipUserValidity(final String token) throws GenericProTipServiceException,
                                                                      TokenExpiredException,
                                                                      ParseException;
    Role getRole(final String token) throws GenericProTipServiceException, TokenExpiredException;
}
