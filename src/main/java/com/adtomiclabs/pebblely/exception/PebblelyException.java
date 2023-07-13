package com.adtomiclabs.pebblely.exception;

/**
 * Exception thrown when an error occurs in Pebblely operations.
 */
public class PebblelyException extends RuntimeException {

    /**
     * Constructs a new {@code PebblelyException} with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public PebblelyException(String message, Throwable cause) {
        super(message, cause);
    }

}
