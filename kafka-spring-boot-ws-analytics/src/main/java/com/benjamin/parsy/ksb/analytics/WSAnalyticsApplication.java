package com.benjamin.parsy.ksb.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class WSAnalyticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WSAnalyticsApplication.class, args);
    }

}
