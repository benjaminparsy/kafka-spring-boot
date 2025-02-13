package com.benjamin.parsy.ksb.order.infrastructure.event.kafka;

import com.benjamin.parsy.ksb.order.entity.event.EventListener;
import com.benjamin.parsy.ksb.order.entity.exception.OrderNotFoundException;
import com.benjamin.parsy.ksb.order.infrastructure.configuration.KafkaConstant;
import com.benjamin.parsy.ksb.order.usecase.CancelOrderUseCase;
import com.benjamin.parsy.ksb.order.usecase.ConfirmedOrderUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEventListener implements EventListener<GenericRecord> {

    private final CancelOrderUseCase cancelOrderUseCase;
    private final ConfirmedOrderUseCase confirmOrderUseCase;

    @Override
    @KafkaListener(topics = KafkaConstant.TOPIC_ORDER_FAILED, groupId = KafkaConstant.GROUP_ID_ORDER)
    public void handleCancelOrder(GenericRecord orderFailedEvent) {

        UUID orderUuid = UUID.fromString(orderFailedEvent.get("orderUuid").toString());
        String cause = orderFailedEvent.get("cause").toString();

        try {
            cancelOrderUseCase.cancelOrder(orderUuid, cause);
        } catch (OrderNotFoundException e) {
            log.error("An error occurred when cancelling the order at id {}", orderUuid, e);
        }

    }

    @Override
    @KafkaListener(topics = KafkaConstant.TOPIC_ORDER_PAID, groupId = KafkaConstant.GROUP_ID_ORDER)
    public void handleOrderPaid(GenericRecord orderPaidEvent) {

        UUID orderUuid = UUID.fromString(orderPaidEvent.get("orderUuid").toString());

        try {
            confirmOrderUseCase.confirmOrder(orderUuid);
        } catch (OrderNotFoundException e) {
            log.error("An error occurred during order confirmation at id {}", orderUuid, e);
        }

    }

}
