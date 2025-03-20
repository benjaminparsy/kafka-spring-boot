package com.benjamin.parsy.runnetic.order.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableKafka
@EnableTransactionManagement
@EnableCaching
@SpringBootApplication
public class WSOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WSOrderApplication.class, args);
    }

}
