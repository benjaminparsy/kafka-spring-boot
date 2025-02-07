package com.benjamin.parsy.ksb.user.starter;

import com.benjamin.parsy.ksb.user.domain.service.UserService;
import com.benjamin.parsy.ksb.user.infrastructure.jpa.UserRepositoryJpaImpl;
import com.benjamin.parsy.ksb.user.infrastructure.kafka.UserEventPublisherKafkaImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainBeanConnector {

    @Bean
    public UserService orderService(UserRepositoryJpaImpl userRepositoryJpaImpl,
                                    UserEventPublisherKafkaImpl userEventPublisherKafkaImpl) {
        return new UserService(userRepositoryJpaImpl, userEventPublisherKafkaImpl);
    }

}
