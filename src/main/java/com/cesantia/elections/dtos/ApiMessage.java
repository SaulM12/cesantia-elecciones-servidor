package com.cesantia.elections.dtos;

public class ApiMessage {
    private String message;

    public ApiMessage(String message) {
        this.message = message;
    }

    // Getter y Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
