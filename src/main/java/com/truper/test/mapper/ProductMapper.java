package com.truper.test.mapper;

import com.truper.test.dto.request.product.ProductRequest;
import com.truper.test.dto.response.ProductResponse;
import com.truper.test.model.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductEntity mapToEntity(ProductRequest request) {
        return ProductEntity.builder()
                .code(request.code())
                .description(request.description())
                .price(request.price())
                .build();
    }

    public ProductResponse mapToResponse(ProductEntity product) {
        return new ProductResponse(
                product.getId(),
                product.getCode(),
                product.getDescription(),
                product.getPrice()
        );
    }
}
