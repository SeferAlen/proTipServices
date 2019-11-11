package com.protip.proTipServices.exceptions;

/**
 * The type PasswordIncorrect exception.
 */
public class PasswordIncorrectException extends Exception {

    /**
     * Instantiates a new Password incorrect exception.
     *
     * @param message {@link String} the message
     */
    public PasswordIncorrectException(final String message) {
        super(message);
    }
}
