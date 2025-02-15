package com.benjamin.parsy.ksb.user.infrastructure.db;

import com.benjamin.parsy.ksb.user.infrastructure.configuration.KafkaConstant;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@ActiveProfiles("test")
@EmbeddedKafka(
        partitions = 1,
        topics = {
                KafkaConstant.Producer.TOPIC_ORDER_FAILED,
                KafkaConstant.Producer.TOPIC_USER_VALIDATED,
        },
        controlledShutdown = true
)
@Slf4j
public abstract class ITest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @PostConstruct
    public void checkEmbeddedKafka() {

        log.info("Waiting for Kafka embedded servers to start up...");

        await().atMost(10, TimeUnit.SECONDS)
                .until(() -> embeddedKafka.getBrokersAsString().contains(":"));

        log.info("Embedded Kafka is running.");
    }

}
