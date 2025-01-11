package com.benjamin.parsy.ksb.order.order.kafka;

import com.benjamin.parsy.ksb.order.kafkaevent.KafkaEventService;
import com.benjamin.parsy.ksb.order.order.Order;
import com.benjamin.parsy.ksb.order.orderproduct.OrderProduct;
import com.benjamin.parsy.ksb.shared.exception.ErrorCode;
import com.benjamin.parsy.ksb.shared.exception.GlobalException;
import com.benjamin.parsy.ksb.shared.service.MessageService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KafkaProducerOrderService {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private final KafkaEventService kafkaEventService;
    private final MessageService messageService;

    public KafkaProducerOrderService(KafkaTemplate<String, OrderEvent> kafkaTemplate, KafkaEventService kafkaEventService, MessageService messageService) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaEventService = kafkaEventService;
        this.messageService = messageService;
    }

    public void send(String topic, List<OrderProduct> orderProductList) throws GlobalException {

        if (orderProductList.isEmpty()) {
            throw new GlobalException(messageService.getErrorMessage(ErrorCode.KAFKA_MESSAGE_CANNOT_BE_CREATED));
        }

        Order order = orderProductList.getFirst().getOrder();

        for (OrderProduct o : orderProductList) {
            if (!o.getOrder().equals(order)) {
                throw new GlobalException(messageService.getErrorMessage(ErrorCode.KAFKA_MESSAGE_CANNOT_BE_CREATED));
            }
        }

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderId(order.getId());
        orderEvent.setUserProjectionId(order.getUserProjection().getId());
        orderEvent.setOrderDate(order.getOrderDate());
        orderEvent.setOrderStatus(order.getOrderStatusEnum().name());
        orderEvent.setOrderTotal(order.getOrderTotal());
        orderEvent.setProductList(orderProductList.stream()
                .map(o -> new OrderEvent.Product(o.getProductId(), o.getQuantity()))
                .collect(Collectors.toCollection(LinkedList::new)));

        try {
            kafkaEventService.save(topic, orderEvent);
            kafkaTemplate.send(topic, orderEvent);
        } catch (Exception e) {
            throw new GlobalException(messageService.getErrorMessage(ErrorCode.KAFKA_MESSAGE_CANNOT_BE_CREATED));
        }

    }

}
