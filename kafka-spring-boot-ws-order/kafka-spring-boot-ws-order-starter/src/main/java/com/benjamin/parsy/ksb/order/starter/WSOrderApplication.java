package com.benjamin.parsy.ksb.order.starter;

import com.benjamin.parsy.ksb.order.application.ApplicationContext;
import com.benjamin.parsy.ksb.order.infrastructure.InfrastructureContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableKafka
@EnableTransactionManagement
@EnableCaching
@SpringBootApplication
@ImportAutoConfiguration({
        ApplicationContext.class,
        InfrastructureContext.class
})
public class WSOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WSOrderApplication.class, args);
    }

}
