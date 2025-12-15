package com.truper.test.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record OrderPricesRequest(
        @NotNull
        @Positive
        Integer id,
        @NotEmpty
        @Valid
        List<ProductPriceRequest> products
) {
}
