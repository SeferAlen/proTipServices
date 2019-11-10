package com.protip.proTipServices.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

/**
 * Class for message types
 */
@Entity
@Table(name = "messageType")
public class MessageType {

    @GeneratedValue
    @Id
    @Column(name = "idMessageType")
    private UUID idMessageType;
    @NotNull
    @Column(name = "type")
    private String messageType;
    @OneToMany(mappedBy = "messageType", cascade = CascadeType.ALL)
    private Set<Message> type;

    /**
     * Instantiates a new Message type.
     *
     * @param messageType the message type
     */
    public MessageType(@NotNull String messageType) {
        this.messageType = messageType;
    }

    /**
     * Gets id message type.
     *
     * @return the id message type
     */
    public UUID getIdMessageType() {
        return idMessageType;
    }

    /**
     * Sets id message type.
     *
     * @param idMessageType the id message type
     */
    public void setIdMessageType(UUID idMessageType) {
        this.idMessageType = idMessageType;
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
