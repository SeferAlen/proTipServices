package com.protip.proTipServices.model;

import com.protip.proTipServices.utility.ProTipUserActionStatus;

import java.util.List;

/**
 * The type Get messages.
 */
public class GetMessages {

    private List<SendMessage> messages;
    private ProTipUserActionStatus userStatus;

    /**
     * Instantiates a new Get messages.
     *
     * @param messages   the messages
     * @param userStatus the user status
     */
    public GetMessages(List<SendMessage> messages, ProTipUserActionStatus userStatus) {
        this.messages = messages;
        this.userStatus = userStatus;
    }

    /**
     * Gets messages.
     *
     * @return the messages
     */
    public List<SendMessage> getMessages() {
        return messages;
    }

    /**
     * Sets messages.
     *
     * @param messages the messages
     */
    public void setMessages(List<SendMessage> messages) {
        this.messages = messages;
    }

    /**
     * Gets user status.
     *
     * @return the user status
     */
    public ProTipUserActionStatus getUserStatus() {
        return userStatus;
    }

    /**
     * Sets user status.
     *
     * @param userStatus the user status
     */
    public void setUserStatus(ProTipUserActionStatus userStatus) {
        this.userStatus = userStatus;
    }
}
