package com.protip.proTipServices.exceptions;

import java.util.Date;

/**
 * Class for containing error's in customized exceptions
 */
public class ErrorDetails {

    private Date timestamp;
    private String message;
    private String details;

    /**
     * Instantiates a new Error details.
     *
     * @param timestamp the timestamp
     * @param message   the message
     * @param details   the details
     */
    public ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets details.
     *
     * @return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets details.
     *
     * @param details the details
     */
    public void setDetails(String details) {
        this.details = details;
    }
}
