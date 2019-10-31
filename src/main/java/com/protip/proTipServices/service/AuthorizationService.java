package com.protip.proTipServices.service;

import com.protip.proTipServices.exceptions.GenericProTipServiceException;
import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.model.Role;

public interface AuthorizationService {

    boolean authorizeUser(final String token) throws GenericProTipServiceException, TokenExpiredException;
    boolean authorizeAdmin(final String token) throws GenericProTipServiceException, TokenExpiredException;
    Role getRole(final String token) throws GenericProTipServiceException, TokenExpiredException;
}
