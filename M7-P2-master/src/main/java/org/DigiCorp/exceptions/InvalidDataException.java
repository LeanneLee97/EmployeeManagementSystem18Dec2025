package org.DigiCorp.exceptions;

/**
 * Custom exception class thrown when facing invalid data
 */
public class InvalidDataException extends Exception {

    /**
     * The HTTP status code associated with the exception (e.g., 400, 404).
     */
    private final int statusCode;

    /**
     * Constructs a new InvalidDataException with a specified detail message and HTTP status code.
     *
     * @param message The detail message describing the reason for the error.
     * @param statusCode The corresponding HTTP status code (e.g., 400 Bad Request, 404 Not Found).
     */
    public InvalidDataException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    /**
     * Retrieves the HTTP status code associated with this exception.
     *
     * @return The HTTP status code.
     */
    public int getStatusCode() {
        return statusCode;
    }
}