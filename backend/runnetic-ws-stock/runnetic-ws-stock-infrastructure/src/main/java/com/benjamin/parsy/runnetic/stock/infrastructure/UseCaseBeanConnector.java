package com.benjamin.parsy.runnetic.stock.infrastructure;

import com.benjamin.parsy.runnetic.stock.infrastructure.db.jpa.port.JpaStockPort;
import com.benjamin.parsy.runnetic.stock.infrastructure.event.kafka.port.KafkaEventPort;
import com.benjamin.parsy.runnetic.stock.usecase.ReservedStockUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBeanConnector {

    @Bean
    public ReservedStockUseCase createOrderUseCase(JpaStockPort jpaStockGateway,
                                                   KafkaEventPort kafkaEventPublisher) {
        return new ReservedStockUseCase(jpaStockGateway, kafkaEventPublisher);
    }

}
