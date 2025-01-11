package com.benjamin.parsy.ksb.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication(scanBasePackages = {
        "com.benjamin.parsy.ksb.shared",
        "com.benjamin.parsy.ksb.order",
})
public class WSOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WSOrderApplication.class, args);
    }

}
