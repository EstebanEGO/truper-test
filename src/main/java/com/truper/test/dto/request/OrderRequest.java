package com.truper.test.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record OrderRequest(
        @NotNull
        @Positive
        Integer branchId,
        @NotEmpty
        @Valid
        List<ProductRequest> products
) {
}
