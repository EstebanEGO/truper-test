package com.truper.test.controller;

import com.truper.test.dto.request.order.OrderPricesRequest;
import com.truper.test.dto.request.order.OrderRequest;
import com.truper.test.dto.response.OnlyId;
import com.truper.test.dto.response.OrderResponse;
import com.truper.test.service.order.IReadOrderService;
import com.truper.test.service.order.IWriteOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    @Autowired
    private IReadOrderService readService;

    @Autowired
    private IWriteOrderService writeService;

    @PostMapping
    public ResponseEntity<OnlyId> store(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.status(201).body(writeService.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> show(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(readService.findById(id));
    }

    @PutMapping
    public ResponseEntity<OnlyId> updatePrices(@Valid @RequestBody OrderPricesRequest request) {
        return ResponseEntity.status(200).body(writeService.updatePrices(request));
    }
}