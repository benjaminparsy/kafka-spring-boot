package com.benjamin.parsy.runnetic.stock.infrastructure;

import com.benjamin.parsy.runnetic.stock.infrastructure.db.jpa.gateway.JpaStockGateway;
import com.benjamin.parsy.runnetic.stock.infrastructure.event.kafka.gateway.KafkaEventGateway;
import com.benjamin.parsy.runnetic.stock.usecase.ReservedStockUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBeanConnector {

    @Bean
    public ReservedStockUseCase createOrderUseCase(JpaStockGateway jpaStockGateway,
                                                   KafkaEventGateway kafkaEventPublisher) {
        return new ReservedStockUseCase(jpaStockGateway, kafkaEventPublisher);
    }

}
