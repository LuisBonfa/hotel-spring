package com.alten.hotel.common.exception.exceptions;

/**
 * Exception created to handle all the not founds exceptions.
 *
 * @author luis.bonfa
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
