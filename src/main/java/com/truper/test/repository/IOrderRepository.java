package com.truper.test.repository;

import com.truper.test.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<OrderEntity, Integer> {
}
