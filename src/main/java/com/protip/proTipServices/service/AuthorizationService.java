package com.protip.proTipServices.service;

import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.model.Role;
import com.protip.proTipServices.utility.ProTipValidityStatus;

import java.text.ParseException;

public interface AuthorizationService {

    public abstract boolean authorizeUser(final String token) throws GenericProTipServiceException, TokenExpiredException;
    public abstract boolean authorizeAdmin(final String token) throws GenericProTipServiceException, TokenExpiredException;
    public abstract ProTipValidityStatus checkProTipUserValidity(final String token) throws GenericProTipServiceException,
                                                                                            TokenExpiredException,
                                                                                            ParseException;
    public abstract Role getRole(final String token) throws GenericProTipServiceException, TokenExpiredException;
}
