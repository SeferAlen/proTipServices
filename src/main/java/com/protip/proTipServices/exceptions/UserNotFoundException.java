package com.protip.proTipServices.exceptions;

/**
 * The type UserNotFound exception.
 */
public class UserNotFoundException extends Exception {

    /**
     * Instantiates a new User not found exception.
     *
     * @param message {@link String} the message
     */
    public UserNotFoundException(final String message) {
        super(message);
    }
}
