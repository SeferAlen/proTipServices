package com.protip.proTipServices.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * Class for messages
 */
@Entity
@Table(name = "message")
public class Message {

    @GeneratedValue
    @Id
    @Column(name = "idMessage")
    private UUID idMessage;
    @NotNull
    @ManyToOne
    private ProTipUser sender;
    @NotNull
    @Column(name = "message", length = 500)
    private String message;
    @NotNull
    @Column(name = "dateTime")
    private Date dateTime;
    @ManyToOne
    @JoinColumn
    private MessageType messageType;

    /**
     * Instantiates a new Message.
     */
    public Message() {
    }

    /**
     * Instantiates a new Message.
     *
     * @param sender      the sender
     * @param message     the message
     * @param dateTime    the date time
     * @param messageType the message type
     */
    public Message(@NotNull ProTipUser sender, @NotNull String message, @NotNull Date dateTime, MessageType messageType) {
        this.sender = sender;
        this.message = message;
        this.dateTime = dateTime;
        this.messageType = messageType;
    }

    /**
     * Gets id message.
     *
     * @return the id message
     */
    public UUID getIdMessage() {
        return idMessage;
    }

    /**
     * Gets sender.
     *
     * @return the sender
     */
    public ProTipUser getSender() {
        return sender;
    }

    /**
     * Sets sender.
     *
     * @param sender the sender
     */
    public void setSender(ProTipUser sender) {
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
     * Gets date time.
     *
     * @return the date time
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**
     * Sets date time.
     *
     * @param dateTime the date time
     */
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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

    /**
     * Gets sender name.
     *
     * @return the sender name
     */
    public String getSenderName() {
        return getSender().getFirstName() + " " + getSender().getLastName();
    }
}
