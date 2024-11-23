package com.tove.enterprise_project.exception;


import java.time.OffsetDateTime;
import java.util.List;

public record ErrorResponse(
        int statusCode,
        String message,
        OffsetDateTime timeStamp,
        List<ValidationError> validationErrors
) {
    public ErrorResponse(int statusCode, String message, OffsetDateTime timeStamp) {
        this(statusCode, message, timeStamp, List.of());
    }

    public record ValidationError(
            String field,
            String error
    ){

    }
}
