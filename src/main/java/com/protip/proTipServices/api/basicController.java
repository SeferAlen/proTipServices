package com.protip.proTipServices.api;

import org.springframework.http.HttpStatus;

/**
 * Abstract basic REST controller
 */
public abstract class basicController {
    protected static final HttpStatus HTTP_OK = HttpStatus.OK;
    protected static final HttpStatus HTTP_CREATED = HttpStatus.CREATED;
    protected static final HttpStatus HTTP_BAD_REQUEST = HttpStatus.BAD_REQUEST;
    protected static final HttpStatus HTTP_UNAUTHORIZED = HttpStatus.UNAUTHORIZED;
    protected static final HttpStatus HTTP_INTERNAL_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;
    protected static final String USER_AUTHORIZED = "User is authorized";
    protected static final String USER_UNAUTHORIZED = "User not authorized, cannot proceed";
    protected static final String ROLE_USER = "USER";
    protected static final String ROLE_ADMIN = "ADMIN";
    protected static final String DATE_FORMAT = "yyyy-MM-dd";
    protected static final String EMPTY_SPACE = " ";
    protected static final String SERVICE_ERROR_MESSAGE = "Service error";
    protected static final String SERVICE_ERROR_DETAILS = "Please contact us with about this";
}
