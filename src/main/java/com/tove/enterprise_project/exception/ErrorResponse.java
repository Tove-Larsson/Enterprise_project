package com.tove.enterprise_project.exception;


import java.util.List;

public record ErrorResponse(
        int statusCode,
        String message,
        String timeStamp,
        List<ValidationError> validationErrors
) {
    public ErrorResponse(int statusCode, String message, String timeStamp) {
        this(statusCode, message, timeStamp, List.of());
    }

    public record ValidationError(
            String field,
            String error
    ){

    }
}
