package com.protip.proTipServices.exceptions;

/**
 * The type TokenExpired exception.
 */
public class TokenExpiredException extends Exception {

    /**
     * Instantiates a new Token expired exception.
     *
     * @param message {@link String} the message
     */
    public TokenExpiredException(final String message) {
        super(message);
    }
}
