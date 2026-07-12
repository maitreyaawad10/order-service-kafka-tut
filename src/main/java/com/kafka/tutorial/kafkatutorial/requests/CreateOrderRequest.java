package com.kafka.tutorial.kafkatutorial.requests;

import com.kafka.tutorial.kafkatutorial.events.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderRequest(
        String customerId,
        List<OrderItem> items,
        BigDecimal totalAmount
) {}
