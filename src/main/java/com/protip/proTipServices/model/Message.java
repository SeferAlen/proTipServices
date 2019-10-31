package com.protip.proTipServices.model;

import com.protip.proTipServices.utility.MessageType;

/**
 * The type Message.
 */
public class Message {

    private String message;
    private String sender;
    private MessageType messageType;

    /**
     * Instantiates a new Message.
     */
    public Message() {
    }

    /**
     * Instantiates a new Message.
     *
     * @param message     the message
     * @param sender      the sender
     * @param messageType the message type
     */
    public Message(String message, String sender, MessageType messageType) {
        this.message = message;
        this.sender = sender;
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
     * Gets message type.
     *
     * @return the message type
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * Sets message type.
     *
     * @param messageType the message type
     */
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
