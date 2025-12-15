package com.truper.test.service.order;

import com.truper.test.dto.response.OrderResponse;
import org.springframework.stereotype.Service;

@Service
public interface IReadOrderService {
    OrderResponse findById(Integer id);
}
