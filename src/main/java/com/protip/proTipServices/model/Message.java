package com.protip.proTipServices.model;

/**
 * The type Message.
 */
public class Message {

    private String message;
    private String sender;

    /**
     * Instantiates a new Message.
     *
     * @param message the message
     * @param sender  the sender
     */
    public Message(String message, String sender) {
        this.message = message;
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
}
