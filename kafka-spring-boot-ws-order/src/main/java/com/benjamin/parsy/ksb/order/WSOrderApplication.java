package com.benjamin.parsy.ksb.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableKafka
@EnableTransactionManagement
@EntityScan(value = "com.benjamin.parsy.ksb")
@EnableJpaRepositories(value = "com.benjamin.parsy.ksb")
@EnableCaching
@SpringBootApplication(scanBasePackages = {
        "com.benjamin.parsy.ksb.shared",
        "com.benjamin.parsy.ksb.order",
})
public class WSOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WSOrderApplication.class, args);
    }

}
