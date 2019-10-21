package com.protip.proTipServices.service;

import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.TokenExpiredException;

public interface AuthorizationService {

    boolean authorizeUser(final String token) throws GenericProTipServiceException, TokenExpiredException;
    boolean authorizeAdmin(final String token) throws GenericProTipServiceException, TokenExpiredException;
}
