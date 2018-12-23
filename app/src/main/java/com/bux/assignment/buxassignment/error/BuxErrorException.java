package com.bux.assignment.buxassignment.error;

public class BuxErrorException extends RuntimeException {
    private final BuxError buxError;

    public BuxErrorException(BuxError buxError) {
        this.buxError = buxError;
    }

    public BuxError getBuxError() {
        return buxError;
    }
}
