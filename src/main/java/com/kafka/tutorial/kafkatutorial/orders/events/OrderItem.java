package com.kafka.tutorial.kafkatutorial.orders.events;

public record OrderItem(
        String productId,
        Integer quantity
) {}