package com.alten.hotel.common.exception.exceptions;

/**
 * Exception created to handle all the unauthorized exceptions.
 *
 * @author luis.bonfa
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
