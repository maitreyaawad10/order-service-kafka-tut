package com.kafka.tutorial.orders.requests;

import com.kafka.tutorial.orders.events.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderRequest(
        String customerId,
        List<OrderItem> items,
        BigDecimal totalAmount
) {}
