package com.benjamin.parsy.ksb.user.starter;

import com.benjamin.parsy.ksb.user.infrastructure.InfrastructureContext;
import com.benjamin.parsy.ksb.user.application.ApplicationContext;
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
public class WSUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(WSUserApplication.class, args);
    }

}
