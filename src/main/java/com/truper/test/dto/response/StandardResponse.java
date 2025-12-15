package com.truper.test.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record StandardResponse<D>(
        LocalDateTime dateTime,
        String message,
        D data
) {
}
