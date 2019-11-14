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
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy="messageType", cascade = CascadeType.ALL)
    private Set<Message> messages;

    /**
     * Instantiates a new Message type.
     */
    public MessageType() {
    }

    /**
     * Instantiates a new Message type.
     *
     * @param name the name
     */
    public MessageType(@NotNull String name) {
        this.name = name;
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }
}
