package com.epam.microservices.service.exception;

import java.text.MessageFormat;

public class SongNotFoundException extends RuntimeException{
    private static final String ERROR_MESSAGE_PATTERN = "Song with id {0} not found";

    public SongNotFoundException(int resourceId) {
        super(MessageFormat.format(ERROR_MESSAGE_PATTERN, resourceId));
    }
}
