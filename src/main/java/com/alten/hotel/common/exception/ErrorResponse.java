package com.alten.hotel.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Default object to be return when an exception is thrown
 *
 * @author luis.bonfa
 */
@Getter
@Setter
public class ErrorResponse {
    private String httpStatus;
    private String message;
    private String cause;
    private String stacktrace;
}
