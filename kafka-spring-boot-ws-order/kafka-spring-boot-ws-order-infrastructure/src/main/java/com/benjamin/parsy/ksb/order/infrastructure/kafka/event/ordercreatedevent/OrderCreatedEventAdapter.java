package com.benjamin.parsy.ksb.order.infrastructure.kafka.event.ordercreatedevent;

import com.benjamin.parsy.ksb.order.domain.model.DesiredProduct;
import com.benjamin.parsy.ksb.order.domain.model.event.OrderCreatedEvent;
import com.benjamin.parsy.ksb.order.infrastructure.kafka.event.EventKafkaAdapter;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedEventAdapter implements EventKafkaAdapter<OrderCreatedEvent, OrderCreatedKafkaEvent> {

    @Override
    public OrderCreatedKafkaEvent toKafkaEvent(OrderCreatedEvent modelEvent) {
        return new OrderCreatedKafkaEvent(
                modelEvent.getOrderUuid(),
                modelEvent.getUserUuid(),
                modelEvent.getOrderDate().toString(),
                modelEvent.getStatus().name(),
                modelEvent.getProducts().stream()
                        .map(this::toProduct)
                        .toList(),
                modelEvent.getTotalPrice()
        );
    }

    private OrderCreatedKafkaEvent.Product toProduct(DesiredProduct desiredProduct) {
        return new OrderCreatedKafkaEvent.Product(
                desiredProduct.productUuid(),
                desiredProduct.quantity(),
                desiredProduct.price()
        );
    }

}
