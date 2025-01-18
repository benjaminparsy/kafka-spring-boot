package com.benjamin.parsy.ksb.order.stockprojection.kafka;

import com.benjamin.parsy.ksb.order.shared.KafkaConstant;
import com.benjamin.parsy.ksb.order.shared.exception.StockProjectionNotFoundException;
import com.benjamin.parsy.ksb.order.stockprojection.StockProjectionService;
import com.benjamin.parsy.ksb.shared.kafka.KafkaEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerStockProjectionService {

    private final StockProjectionService stockProjectionService;
    private final KafkaEventService kafkaEventService;

    @KafkaListener(topics = KafkaConstant.TOPIC_STOCK_UPDATED, groupId = KafkaConstant.GROUP_ID_STOCK)
    public void handleStockUpdated(StockProjectionEvent stockUpdated) throws JsonProcessingException, StockProjectionNotFoundException {

        kafkaEventService.save(KafkaConstant.TOPIC_STOCK_UPDATED, stockUpdated);

        stockProjectionService.updateStock(stockUpdated.getProductId(), stockUpdated.getProductName(),
                stockUpdated.getPrice(), stockUpdated.getStockQuantity());

        log.debug("The stock has been updated : {}", stockUpdated);
    }

}
