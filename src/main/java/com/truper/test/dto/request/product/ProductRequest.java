package com.truper.test.dto.request.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
        @NotBlank
        //@Max(value = 20)
        String code,
        @NotBlank
        //@Max(value = 200)
        String description,
        @NotNull
        @Min(value = 1)
        Double price
) {
}
