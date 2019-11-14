package com.protip.proTipServices.model;

import java.util.Date;

/**
 * The type Send message.
 */
public class SendMessage {

    private String sender;
    private String message;
    private Date time;

    /**
     * Instantiates a new Send message.
     *
     * @param sender  the sender
     * @param message the message
     * @param time    the time
     */
    public SendMessage(String sender, String message, Date time) {
        this.sender = sender;
        this.message = message;
        this.time = time;
    }

    /**
     * Gets sender.
     *
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Sets sender.
     *
     * @param sender the sender
     */
    public void setSender(String sender) {
        this.sender = sender;
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
     * Gets time.
     *
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(Date time) {
        this.time = time;
    }
}
