package com.protip.proTipServices.model;

/**
 * Class for receiving messages over http POST method
 */
public class ReceivedMessage {

    private String message;
    private String messageType;

    /**
     * Instantiates a new Received message.
     *
     * @param message     the message
     * @param messageType the message type
     */
    public ReceivedMessage(String message, String messageType) {
        this.message = message;
        this.messageType = messageType;
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
     * Gets message type.
     *
     * @return the message type
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * Sets message type.
     *
     * @param messageType the message type
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
