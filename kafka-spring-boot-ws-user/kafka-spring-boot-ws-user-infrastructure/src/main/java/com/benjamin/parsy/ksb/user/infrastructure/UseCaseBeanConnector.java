package com.benjamin.parsy.ksb.user.infrastructure;

import com.benjamin.parsy.ksb.user.infrastructure.db.gateway.JpaUserGateway;
import com.benjamin.parsy.ksb.user.infrastructure.event.kafka.gateway.KafkaEventGateway;
import com.benjamin.parsy.ksb.user.usecase.ValidatedUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBeanConnector {

    @Bean
    public ValidatedUserUseCase createOrderUseCase(JpaUserGateway jpaUserGateway,
                                                   KafkaEventGateway kafkaOrderEventPublisher) {
        return new ValidatedUserUseCase(jpaUserGateway, kafkaOrderEventPublisher);
    }

}
