package com.benjamin.parsy.runnetic.user.infrastructure;

import com.benjamin.parsy.runnetic.user.infrastructure.db.gateway.JpaUserGateway;
import com.benjamin.parsy.runnetic.user.infrastructure.event.kafka.gateway.KafkaEventGateway;
import com.benjamin.parsy.runnetic.user.usecase.ValidatedUserUseCase;
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
