package com.benjamin.parsy.ksb.order.infrastructure;

import com.benjamin.parsy.ksb.order.infrastructure.db.jpa.gateway.JpaOrderGateway;
import com.benjamin.parsy.ksb.order.infrastructure.event.kafka.KafkaEventPublisher;
import com.benjamin.parsy.ksb.order.usecase.CancelOrderUseCase;
import com.benjamin.parsy.ksb.order.usecase.ConfirmedOrderUseCase;
import com.benjamin.parsy.ksb.order.usecase.CreateOrderUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBeanConnector {

    @Bean
    public CreateOrderUseCase createOrderUseCase(JpaOrderGateway jpaOrderGateway,
                                                 KafkaEventPublisher kafkaOrderEventPublisher) {
        return new CreateOrderUseCase(jpaOrderGateway, kafkaOrderEventPublisher);
    }

    @Bean
    public ConfirmedOrderUseCase confirmedOrderUseCase(JpaOrderGateway jpaOrderGateway,
                                                       KafkaEventPublisher kafkaOrderEventPublisher) {
        return new ConfirmedOrderUseCase(jpaOrderGateway, kafkaOrderEventPublisher);
    }

    @Bean
    public CancelOrderUseCase cancelOrderUseCase(JpaOrderGateway jpaOrderGateway,
                                                 KafkaEventPublisher kafkaOrderEventPublisher) {
        return new CancelOrderUseCase(jpaOrderGateway, kafkaOrderEventPublisher);
    }

}
