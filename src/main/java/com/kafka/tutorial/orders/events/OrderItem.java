package com.kafka.tutorial.orders.events;

public record OrderItem(
        String productId,
        Integer quantity
) {}