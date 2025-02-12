package com.benjamin.parsy.ksb.order.infrastructure.event.kafka;

import com.benjamin.parsy.ksb.order.entity.event.EventListener;
import com.benjamin.parsy.ksb.order.entity.exception.OrderNotFoundException;
import com.benjamin.parsy.ksb.order.entity.model.event.OrderFailedEvent;
import com.benjamin.parsy.ksb.order.entity.model.event.OrderPaidEvent;
import com.benjamin.parsy.ksb.order.infrastructure.configuration.KafkaConstant;
import com.benjamin.parsy.ksb.order.usecase.CancelOrderUseCase;
import com.benjamin.parsy.ksb.order.usecase.ConfirmedOrderUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEventListener implements EventListener {

    private final CancelOrderUseCase cancelOrderUseCase;
    private final ConfirmedOrderUseCase confirmOrderUseCase;

    @Override
    @KafkaListener(topics = KafkaConstant.TOPIC_ORDER_FAILED, groupId = KafkaConstant.GROUP_ID_ORDER)
    public void handleCancelOrder(OrderFailedEvent event) {

        try {
            cancelOrderUseCase.cancelOrder(event.getUuid(), event.getCause());
        } catch (OrderNotFoundException e) {
            log.error("An error occurred when cancelling the order at id {}", event.uuid, e);
        }

    }

    @Override
    @KafkaListener(topics = KafkaConstant.TOPIC_ORDER_FAILED, groupId = KafkaConstant.GROUP_ID_ORDER)
    public void handleOrderPaid(OrderPaidEvent event) {

        try {
            confirmOrderUseCase.confirmOrder(event.getUuid());
        } catch (OrderNotFoundException e) {
            log.error("An error occurred during order confirmation at id {}", event.uuid, e);
        }

    }

}
