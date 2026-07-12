package com.kafka.tutorial.orders.services;

import com.kafka.tutorial.orders.events.OrderCreatedEvent;
import com.kafka.tutorial.orders.requests.CreateOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class OrderService {
    private static final String ORDERS_TOPIC = "orders";
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderService(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String createOrder(CreateOrderRequest request) {
        String orderId = UUID.randomUUID().toString();

        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(
                orderId,
                request.customerId(),
                request.items(),
                request.totalAmount(),
                Instant.now()
        );

        CompletableFuture<SendResult<String, OrderCreatedEvent>> result = kafkaTemplate.send(
                ORDERS_TOPIC,
                orderId,
                orderCreatedEvent
        );

        result.whenComplete((res, ex) -> {
            if (ex != null) {
                log.error("Something went wrong while publishing to kafka");
            } else {
                log.info("Published OrderCreatedEvent: orderId: {}, topic: {}, partition: {}, offset: {}",
                        orderId,
                        res.getRecordMetadata().topic(),
                        res.getRecordMetadata().partition(),
                        res.getRecordMetadata().offset()
                );
            }
        });

        return orderId;
    }
}
