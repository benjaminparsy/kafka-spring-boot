package com.benjamin.parsy.ksb.order.starter;

import com.benjamin.parsy.ksb.order.domain.service.OrderService;
import com.benjamin.parsy.ksb.order.infrastructure.jpa.OrderRepositoryJpaImpl;
import com.benjamin.parsy.ksb.order.infrastructure.kafka.OrderEventKafkaPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainBeanConnector {

    @Bean
    public OrderService orderService(OrderRepositoryJpaImpl orderRepositoryJpaImpl,
                                     OrderEventKafkaPublisher orderEventKafkaPublisher) {
        return new OrderService(orderRepositoryJpaImpl, orderEventKafkaPublisher);
    }

}
