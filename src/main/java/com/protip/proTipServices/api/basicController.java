package com.protip.proTipServices.api;

import org.springframework.http.HttpStatus;

/**
 * Abstract basic REST controller
 */
public abstract class basicController {
    protected static final HttpStatus HTTP_CREATED = HttpStatus.CREATED;
    protected static final HttpStatus HTTP_BAD_REQUEST = HttpStatus.BAD_REQUEST;
    protected static final HttpStatus HTTP_OK = HttpStatus.OK;
    protected static final String ROLE_USER = "USER";
    protected static final String ROLE_ADMIN = "ADMIN";
    protected static final String DATE_FORMAT = "yyyy-MM-dd";
    protected static final String EMPTY_SPACE = " ";
}
