package com.dci.demo.model;

import javax.validation.constraints.Size;

public class Message {

    @Size(min = 2, message = "Message must be a minimum of 2 characters")
    private String message;

    private String hash;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}