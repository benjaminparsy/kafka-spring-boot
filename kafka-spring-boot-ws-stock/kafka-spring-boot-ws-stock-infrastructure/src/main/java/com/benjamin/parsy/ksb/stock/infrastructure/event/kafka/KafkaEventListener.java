package com.benjamin.parsy.ksb.stock.infrastructure.event.kafka;

import com.benjamin.parsy.ksb.avro.dto.DesiredProductKafkaEvent;
import com.benjamin.parsy.ksb.avro.dto.OrderCreatedKafkaEvent;
import com.benjamin.parsy.ksb.stock.infrastructure.configuration.KafkaConstant;
import com.benjamin.parsy.ksb.stock.infrastructure.dto.DesiredProductPublicData;
import com.benjamin.parsy.ksb.stock.usecase.ReservedStockUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEventListener {

    private final ReservedStockUseCase reservedStockUseCase;

    @KafkaListener(topics = KafkaConstant.Consumer.TOPIC_ORDER_CREATED, groupId = KafkaConstant.GROUP_ID_ORDER)
    public void handleOrderCreated(OrderCreatedKafkaEvent orderCreatedKafkaEvent) {

        UUID orderUuid = UUID.fromString(orderCreatedKafkaEvent.getOrderUuid());
        List<DesiredProductPublicData> productPublicDataList = orderCreatedKafkaEvent.getProducts()
                .stream()
                .map(KafkaEventListener::mapProduct)
                .toList();

        reservedStockUseCase.reservedStock(orderUuid, List.copyOf(productPublicDataList));

    }

    private static DesiredProductPublicData mapProduct(DesiredProductKafkaEvent productKafkaEvent) {

        return new DesiredProductPublicData(
                UUID.fromString(productKafkaEvent.getProductUuid()),
                productKafkaEvent.getQuantity(),
                productKafkaEvent.getPrice()
        );
    }

}
