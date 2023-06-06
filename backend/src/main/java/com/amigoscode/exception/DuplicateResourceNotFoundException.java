package com.amigoscode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT)
public class DuplicateResourceNotFoundException extends  RuntimeException
{
    public DuplicateResourceNotFoundException(String message) {
        super(message);
    }
}
