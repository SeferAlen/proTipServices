package com.protip.proTipServices.model;

import java.util.List;

/**
 * The type Send message and token.
 */
public class GetMessagesResponse {

    private List<SendMessage> messages;
    private String token;

    /**
     * Instantiates a new Get messages response.
     */
    public GetMessagesResponse() {
    }

    /**
     * Instantiates a new Send message and token.
     *
     * @param messages the messages
     * @param token    the token
     */
    public GetMessagesResponse(List<SendMessage> messages, String token) {
        this.messages = messages;
        this.token = token;
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
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
