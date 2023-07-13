package com.adtomiclabs.pebblely.exception;

/**
 * Exception thrown when a requested file is not found in the storage.
 */
public class StorageFileNotFoundException extends StorageException {

	/**
	 * Constructs a new {@code StorageFileNotFoundException} with the specified detail message.
	 *
	 * @param message the detail message.
	 */
	public StorageFileNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructs a new {@code StorageFileNotFoundException} with the specified detail message and cause.
	 *
	 * @param message the detail message.
	 * @param cause   the cause of the exception.
	 */
	public StorageFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
