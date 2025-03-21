package com.benjamin.parsy.runnetic.order.infrastructure;

import com.benjamin.parsy.runnetic.order.infrastructure.db.jpa.port.JpaOrderPort;
import com.benjamin.parsy.runnetic.order.infrastructure.event.kafka.port.KafkaEventPort;
import com.benjamin.parsy.runnetic.order.usecase.CancelOrderUseCase;
import com.benjamin.parsy.runnetic.order.usecase.ConfirmedOrderUseCase;
import com.benjamin.parsy.runnetic.order.usecase.CreateOrderUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseBeanConnector {

    @Bean
    public CreateOrderUseCase createOrderUseCase(JpaOrderPort jpaOrderGateway,
                                                 KafkaEventPort kafkaOrderEventPublisher) {
        return new CreateOrderUseCase(jpaOrderGateway, kafkaOrderEventPublisher);
    }

    @Bean
    public ConfirmedOrderUseCase confirmedOrderUseCase(JpaOrderPort jpaOrderGateway,
                                                       KafkaEventPort kafkaOrderEventPublisher) {
        return new ConfirmedOrderUseCase(jpaOrderGateway, kafkaOrderEventPublisher);
    }

    @Bean
    public CancelOrderUseCase cancelOrderUseCase(JpaOrderPort jpaOrderGateway,
                                                 KafkaEventPort kafkaOrderEventPublisher) {
        return new CancelOrderUseCase(jpaOrderGateway, kafkaOrderEventPublisher);
    }

}
