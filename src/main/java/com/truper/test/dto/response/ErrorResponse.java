package com.truper.test.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        LocalDateTime dateTime,
        Integer statusCode,
        String message,
        Map<String, String> errors
) {
}
