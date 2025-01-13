package com.benjamin.parsy.ksb.order.order.kafka;

import com.benjamin.parsy.ksb.order.order.Order;
import com.benjamin.parsy.ksb.order.orderproduct.OrderProduct;
import com.benjamin.parsy.ksb.order.shared.OrderErrorCode;
import com.benjamin.parsy.ksb.order.shared.exception.EmptyOrderProductListException;
import com.benjamin.parsy.ksb.order.shared.exception.InvalidOrderException;
import com.benjamin.parsy.ksb.shared.exception.AbstractMessageException;
import com.benjamin.parsy.ksb.shared.kafka.KafkaEventService;
import com.benjamin.parsy.ksb.shared.kafka.KafkaProducerGeneric;
import com.benjamin.parsy.ksb.shared.service.message.MessageService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class KafkaProducerOrderService extends KafkaProducerGeneric<List<OrderProduct>, OrderEvent> {

    private final MessageService messageService;

    public KafkaProducerOrderService(KafkaTemplate<String, OrderEvent> kafkaTemplate,
                                     KafkaEventService kafkaEventService,
                                     MessageService messageService) {
        super(kafkaTemplate, kafkaEventService);
        this.messageService = messageService;
    }

    /**
     * Validates OrderProduct list before processing.
     */
    @Override
    protected void validateEntity(List<OrderProduct> orderProductList) throws AbstractMessageException {

        if (CollectionUtils.isEmpty(orderProductList)) {
            throw new EmptyOrderProductListException(
                    messageService.getErrorMessage(OrderErrorCode.KAFKA_MESSAGE_CANNOT_BE_CREATED)
            );
        }

        Order order = orderProductList.getFirst().getOrder();

        boolean hasInconsistentOrders = orderProductList.stream()
                .anyMatch(o -> !o.getOrder().equals(order));

        if (hasInconsistentOrders) {
            throw new InvalidOrderException(
                    messageService.getErrorMessage(OrderErrorCode.KAFKA_MESSAGE_CANNOT_BE_CREATED)
            );
        }

    }

    @Override
    protected OrderEvent toKafkaObject(List<OrderProduct> orderProductList) {

        Order order = orderProductList.getFirst().getOrder();

        return OrderEvent.builder()
                .orderId(order.getId())
                .userProjectionId(order.getUserProjection().getId())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatusEnum().name())
                .orderTotal(order.getOrderTotal())
                .productList(orderProductList.stream()
                        .map(o -> new OrderEvent.Product(o.getProductId(), o.getQuantity()))
                        .toList())
                .build();
    }

}
