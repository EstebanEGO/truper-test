package com.truper.test.service.order;

import com.truper.test.dto.request.OrderPricesRequest;
import com.truper.test.dto.request.OrderRequest;
import com.truper.test.dto.response.OnlyId;
import org.springframework.stereotype.Service;

@Service
public interface IWriteOrderService {
    OnlyId save(OrderRequest request);
    OnlyId updatePrices(OrderPricesRequest request);
}
