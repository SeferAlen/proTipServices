package com.protip.proTipServices.model;

public class Payload {
    private String header;
    private Message message;

    public Payload(String header, Message message) {
        this.header = header;
        this.message = message;
    }
}
