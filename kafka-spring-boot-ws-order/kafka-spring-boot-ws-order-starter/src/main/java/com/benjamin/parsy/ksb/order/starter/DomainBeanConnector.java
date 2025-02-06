package com.benjamin.parsy.ksb.order.starter;

import com.benjamin.parsy.ksb.order.domain.service.OrderService;
import com.benjamin.parsy.ksb.order.infrastructure.jpa.OrderRepositoryJpaImpl;
import com.benjamin.parsy.ksb.order.infrastructure.kafka.OrderEventPublisherKafkaImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainBeanConnector {

    @Bean
    public OrderService orderService(OrderRepositoryJpaImpl orderRepositoryJpaImpl,
                                     OrderEventPublisherKafkaImpl orderEventPublisherKafkaImpl) {
        return new OrderService(orderRepositoryJpaImpl, orderEventPublisherKafkaImpl);
    }

}
