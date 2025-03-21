package com.benjamin.parsy.runnetic.user.infrastructure;

import com.benjamin.parsy.runnetic.user.infrastructure.db.jpa.port.JpaUserPort;
import com.benjamin.parsy.runnetic.user.infrastructure.event.kafka.port.KafkaEventPort;
import com.benjamin.parsy.runnetic.user.usecase.ValidatedUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBeanConnector {

    @Bean
    public ValidatedUserUseCase createOrderUseCase(JpaUserPort jpaUserGateway,
                                                   KafkaEventPort kafkaOrderEventPublisher) {
        return new ValidatedUserUseCase(jpaUserGateway, kafkaOrderEventPublisher);
    }

}
