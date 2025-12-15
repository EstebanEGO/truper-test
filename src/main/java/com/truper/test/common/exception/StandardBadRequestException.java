package com.truper.test.common.exception;

public class StandardBadRequestException extends RuntimeException {
    public StandardBadRequestException(String message) {
        super(message);
    }
}
