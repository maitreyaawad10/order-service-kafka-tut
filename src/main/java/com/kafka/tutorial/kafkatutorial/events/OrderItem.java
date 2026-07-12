package com.kafka.tutorial.kafkatutorial.events;

public record OrderItem(
        String productId,
        Integer quantity
) {}