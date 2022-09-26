package com.alten.hotel.common.exception;

import com.alten.hotel.common.exception.exceptions.GenericException;
import com.alten.hotel.common.exception.exceptions.NotFoundException;
import com.alten.hotel.common.exception.exceptions.UnauthorizedException;
import com.alten.hotel.common.exception.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

/**
 * Main handler of exceptions.
 *
 * @author luis.bonfa
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Used to handle the {@link NotFoundException}
     * httpStatus: NOT_FOUND {@link HttpStatus}
     *
     * @param ex {@link NotFoundException}
     * @return {@link ErrorResponse}
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundException(NotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.NOT_FOUND.name());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStacktrace(Arrays.toString(ex.getStackTrace()));
        errorResponse.setCause(ex.getCause() != null ? String.valueOf(ex.getCause()) : null);
        return errorResponse;
    }

    /**
     * Used to handle the {@link UnauthorizedException}
     * httpStatus: UNAUTHORIZED {@link HttpStatus}
     *
     * @param ex {@link UnauthorizedException}
     * @return {@link ErrorResponse}
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleUnauthorizedException(UnauthorizedException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.UNAUTHORIZED.name());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStacktrace(Arrays.toString(ex.getStackTrace()));
        errorResponse.setCause(ex.getCause() != null ? String.valueOf(ex.getCause()) : null);
        return errorResponse;
    }

    /**
     * Used to handle the {@link ValidationException}
     * httpStatus: BAD_REQUEST {@link HttpStatus}
     *
     * @param ex {@link ValidationException}
     * @return {@link ErrorResponse}
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleValidationException(ValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST.name());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStacktrace(Arrays.toString(ex.getStackTrace()));
        errorResponse.setCause(ex.getCause() != null ? String.valueOf(ex.getCause()) : null);
        return errorResponse;
    }

    /**
     * Used to handle the {@link GenericException}
     * httpStatus: INTERNAL_SERVER_ERROR {@link HttpStatus}
     *
     * @param ex {@link GenericException}
     * @return {@link ErrorResponse}
     */
    @ExceptionHandler(GenericException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleGenericException(GenericException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStacktrace(Arrays.toString(ex.getStackTrace()));
        errorResponse.setCause(ex.getCause() != null ? String.valueOf(ex.getCause()) : null);
        return errorResponse;
    }
}
