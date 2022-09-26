package com.alten.hotel.common.exception.exceptions;

/**
 * Exception created to handle all the validation exceptions.
 *
 * @author luis.bonfa
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
