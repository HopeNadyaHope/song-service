package com.epam.microservices.service.exception;

import java.text.MessageFormat;

public class DuplicateResourceIdException extends RuntimeException {

    private static final String ERROR_MESSAGE_PATTERN = "Duplicate resource id {0}";

    public DuplicateResourceIdException(Integer resourceID) {
        super(MessageFormat.format(ERROR_MESSAGE_PATTERN, resourceID));
    }
}
