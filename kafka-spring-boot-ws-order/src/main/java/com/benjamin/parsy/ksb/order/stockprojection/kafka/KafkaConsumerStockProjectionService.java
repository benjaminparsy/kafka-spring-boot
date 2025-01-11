package com.benjamin.parsy.ksb.order.stockprojection.kafka;

import com.benjamin.parsy.ksb.order.kafka.KafkaConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerStockProjectionService {

    @KafkaListener(topics = KafkaConstant.TOPIC_STOCK_UPDATED, groupId = KafkaConstant.GROUP_ID_STOCK)
    public void handleStockUpdated(String stockUpdated) {
        log.info("The stock has been updated : {}", stockUpdated);
    }

}
