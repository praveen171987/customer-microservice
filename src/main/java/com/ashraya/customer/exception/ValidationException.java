package com.ashraya.customer.exception;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ValidationException(final String message) {
        super(message);
    }
}
