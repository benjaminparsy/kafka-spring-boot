package com.benjamin.parsy.runnetic.order.infrastructure.event.kafka;

import com.benjamin.parsy.runnetic.avro.dto.OrderCanceledKafkaEvent;
import com.benjamin.parsy.runnetic.avro.dto.OrderPaidKafkaEvent;
import com.benjamin.parsy.runnetic.order.usecase.exception.OrderNotFoundException;
import com.benjamin.parsy.runnetic.order.usecase.CancelOrderUseCase;
import com.benjamin.parsy.runnetic.order.usecase.ConfirmedOrderUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEventListener {

    private final CancelOrderUseCase cancelOrderUseCase;
    private final ConfirmedOrderUseCase confirmOrderUseCase;

    @KafkaListener(topics = KafkaConstant.Consumer.TOPIC_ORDER_FAILED, groupId = KafkaConstant.GROUP_ID_ORDER)
    public void handleCancelOrder(OrderCanceledKafkaEvent orderCanceledKafkaEvent) {

        UUID orderUuid = UUID.fromString(orderCanceledKafkaEvent.getOrderUuid());
        String cause = orderCanceledKafkaEvent.getCause();

        try {
            cancelOrderUseCase.cancelOrder(orderUuid, cause);
        } catch (OrderNotFoundException e) {
            log.error("An error occurred when cancelling the order at id {}", orderUuid, e);
        }

    }

    @KafkaListener(topics = KafkaConstant.Consumer.TOPIC_ORDER_PAID, groupId = KafkaConstant.GROUP_ID_ORDER)
    public void handleOrderPaid(OrderPaidKafkaEvent orderPaidKafkaEvent) {

        UUID orderUuid = UUID.fromString(orderPaidKafkaEvent.getOrderUuid());

        try {
            confirmOrderUseCase.confirmOrder(orderUuid);
        } catch (OrderNotFoundException e) {
            log.error("An error occurred during order confirmation at id {}", orderUuid, e);
        }

    }

}
