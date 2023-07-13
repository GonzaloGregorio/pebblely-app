package com.adtomiclabs.pebblely.exception;

/**
 * Exception thrown when an error occurs in the storage operations.
 */
public class StorageException extends RuntimeException {

    /**
     * Constructs a new {@code StorageException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public StorageException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code StorageException} with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

