package com.truper.test.dto.request.order;

import com.truper.test.dto.request.product.ProductPriceRequest;
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
