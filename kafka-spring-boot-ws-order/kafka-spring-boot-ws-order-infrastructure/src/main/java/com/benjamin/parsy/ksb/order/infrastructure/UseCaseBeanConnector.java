package com.benjamin.parsy.ksb.order.infrastructure;

import com.benjamin.parsy.ksb.order.infrastructure.db.gateway.JpaEventGateway;
import com.benjamin.parsy.ksb.order.infrastructure.db.gateway.JpaOrderGateway;
import com.benjamin.parsy.ksb.order.infrastructure.event.kafka.KafkaOrderEventPublisher;
import com.benjamin.parsy.ksb.order.usecase.CancelOrderUseCase;
import com.benjamin.parsy.ksb.order.usecase.ConfirmedOrderUseCase;
import com.benjamin.parsy.ksb.order.usecase.CreateOrderUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBeanConnector {

    @Bean
    public CreateOrderUseCase createOrderUseCase(JpaOrderGateway jpaOrderGateway,
                                                 KafkaOrderEventPublisher kafkaOrderEventPublisher,
                                                 JpaEventGateway jpaEventGateway) {
        return new CreateOrderUseCase(jpaOrderGateway, kafkaOrderEventPublisher, jpaEventGateway);
    }

    @Bean
    public ConfirmedOrderUseCase confirmedOrderUseCase(JpaOrderGateway jpaOrderGateway,
                                                       KafkaOrderEventPublisher kafkaOrderEventPublisher,
                                                       JpaEventGateway jpaEventGateway) {
        return new ConfirmedOrderUseCase(jpaOrderGateway, kafkaOrderEventPublisher, jpaEventGateway);
    }

    @Bean
    public CancelOrderUseCase cancelOrderUseCase(JpaOrderGateway jpaOrderGateway,
                                                 KafkaOrderEventPublisher kafkaOrderEventPublisher,
                                                 JpaEventGateway jpaEventGateway) {
        return new CancelOrderUseCase(jpaOrderGateway, kafkaOrderEventPublisher, jpaEventGateway);
    }

}
