package com.bux.assignment.buxassignment.websocket;

public class WebSocketError {

    private final String errorCode;

    private final String developerMessage;

    public WebSocketError(String errorCode, String developerMessage) {
        this.errorCode = errorCode;
        this.developerMessage = developerMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    @Override
    public String toString() {
        return errorCode + ": " + developerMessage;
    }
}
