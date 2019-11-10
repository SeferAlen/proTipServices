package com.protip.proTipServices.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
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
    @ManyToOne
    @JoinColumn
    private MessageType messageType;

    /**
     * Instantiates a new Message.
     *
     * @param idMessage   the id message
     * @param sender      the sender
     * @param message     the message
     * @param messageType the message type
     */
    public Message(UUID idMessage, @NotNull ProTipUser sender, @NotNull String message, @NotNull MessageType messageType) {
        this.idMessage = idMessage;
        this.sender = sender;
        this.message = message;
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
     * Sets id message.
     *
     * @param idMessage the id message
     */
    public void setIdMessage(UUID idMessage) {
        this.idMessage = idMessage;
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
