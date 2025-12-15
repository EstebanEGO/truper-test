package com.truper.test.service.order;


import com.truper.test.common.exception.ItemNotFoundException;
import com.truper.test.common.exception.StandardBadRequestException;
import com.truper.test.dto.request.order.OrderPricesRequest;
import com.truper.test.dto.request.order.OrderRequest;
import com.truper.test.dto.request.product.ProductPriceRequest;
import com.truper.test.dto.request.product.ProductRequest;
import com.truper.test.dto.response.OnlyId;
import com.truper.test.dto.response.OrderResponse;
import com.truper.test.mapper.ProductMapper;
import com.truper.test.model.entity.BranchEntity;
import com.truper.test.model.entity.OrderEntity;
import com.truper.test.model.entity.ProductEntity;
import com.truper.test.repository.IBranchRepository;
import com.truper.test.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService implements IReadOrderService, IWriteOrderService {

    @Autowired
    private IOrderRepository repository;

    @Autowired
    private IBranchRepository branchRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public OrderResponse findById(Integer id) {

        OrderEntity order = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Order no encontrada con la id: " + id));
        Integer branchId = order.getBranchId();
        BranchEntity branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ItemNotFoundException("Sucursal no encontra con la siguiente id: " + branchId));

        return new OrderResponse(
                order.getId(),
                branch.getName(),
                order.getDate(),
                order.getTotal(),
                order.getProducts().stream().map(productMapper::mapToResponse).toList()
        );
    }

    @Transactional
    @Override
    public OnlyId save(OrderRequest request) {

        Integer branchId = request.branchId();
        BranchEntity branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new ItemNotFoundException("Sucursal no encontra con la siguiente id: " + branchId));
        List<ProductRequest> products = request.products();
        Set<String> productsCodes = products.stream().map(ProductRequest::code).collect(Collectors.toSet());
        //Checamos si existe productos repetidos
        if (productsCodes.size() != products.size()) {
            throw new StandardBadRequestException("Existen productos repetidos");
        }

        //Guardar order
        OrderEntity order = OrderEntity.builder()
                .branchId(branch.getId())
                .products(request.products().stream().map(productMapper::mapToEntity).toList())
                .build();
        order.setTotal(order.calculateTotal());

        Integer idDb = repository.save(order).getId();
        return new OnlyId(idDb);
    }

    @Transactional
    @Override
    public OnlyId updatePrices(OrderPricesRequest request) {
        Integer id = request.id();
        OrderEntity order = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Order no encontrada con la id: " + id));
        List<ProductPriceRequest> products = request.products();
        Set<String> productsCodes = products.stream().map(ProductPriceRequest::code).collect(Collectors.toSet());
        //Checamos si existe productos repetidos
        if (productsCodes.size() != products.size()) {
            throw new StandardBadRequestException("Existen productos repetidos");
        }
        List<ProductEntity> productsDB = order.getProducts();

        //Checamos si existen codigos de productos que no sean originales
        boolean checkCodes = productsCodes.stream().anyMatch(productCode -> productsDB.stream().anyMatch(product -> product.getCode().equals(productCode)));

        if (!checkCodes) {
            throw new StandardBadRequestException("No se encontraron algunos productos");
        }
        products.forEach(product -> {
            productsDB.stream().filter(productDB -> productDB.getCode().equals(product.code()))
                    .findFirst()
                    .ifPresent(productDB -> {
                        productDB.setPrice(product.price());
                    });
        });
        order.setTotal(order.calculateTotal());

        Integer idDb = repository.save(order).getId();
        return new OnlyId(idDb);
    }
}
