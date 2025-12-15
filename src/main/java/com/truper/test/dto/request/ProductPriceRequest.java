package com.truper.test.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductPriceRequest(
        @NotBlank
        String code,
        @NotNull
        Double price
) {
}
