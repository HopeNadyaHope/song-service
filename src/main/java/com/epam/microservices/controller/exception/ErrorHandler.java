package com.epam.microservices.controller.exception;

import com.epam.microservices.service.exception.DuplicateResourceIdException;
import com.epam.microservices.service.exception.SongNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;

@RestControllerAdvice
public class ErrorHandler {
    private static final String FIELD_ERROR_MESSAGE_PATTERN = "{0}: {1}";
    private static final String ERROR_PROCESSING_REQUEST = "There was an error processing the request";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ApiError badRequestException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getFieldError();
        return new ApiError(HttpStatus.BAD_REQUEST,
                MessageFormat.format(FIELD_ERROR_MESSAGE_PATTERN, fieldError.getField(), fieldError.getDefaultMessage()));
    }

    @ExceptionHandler(SongNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ApiError songNotFoundException(SongNotFoundException e) {
        return new ApiError(HttpStatus.NOT_FOUND,
                e.getMessage());
    }

    @ExceptionHandler(DuplicateResourceIdException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ApiError duplicateResourceIdException(DuplicateResourceIdException e) {
        return new ApiError(HttpStatus.CONFLICT,
                e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError otherException() {
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
                ERROR_PROCESSING_REQUEST);
    }

}
