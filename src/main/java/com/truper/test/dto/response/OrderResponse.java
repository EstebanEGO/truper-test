package com.truper.test.dto.response;

import java.time.LocalDate;
import java.util.List;

public record OrderResponse(
        Integer id,
        String branchName,
        LocalDate date,
        Double total,
        List<ProductResponse> products
) {
}
