package com.bux.assignment.buxassignment.error;


public class BuxError {

    private final ErrorCode errorCode;
    private final String message;
    private final String developerMessage;

    public BuxError(ErrorCode errorCode, String message, String developerMessage) {
        this.errorCode = errorCode;
        this.message = message;
        this.developerMessage = developerMessage;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }
}
