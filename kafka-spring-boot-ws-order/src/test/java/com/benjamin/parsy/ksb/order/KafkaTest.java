package com.benjamin.parsy.ksb.order;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.metadata.BrokerState;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.EmbeddedKafkaZKBroker;

import java.util.concurrent.Callable;

import static org.awaitility.Awaitility.await;

@Slf4j
public abstract class KafkaTest extends AbstractTest {

    private final EmbeddedKafkaBroker embeddedKafka;

    public KafkaTest(EmbeddedKafkaBroker embeddedKafka) {
        this.embeddedKafka = embeddedKafka;
    }

    @BeforeEach
    public void setup() {
        log.info("Waiting for kafka embedded servers to start up");
        await().until(embeddedKafkaServersRunning(embeddedKafka));
    }

    private Callable<Boolean> embeddedKafkaServersRunning(EmbeddedKafkaBroker embeddedKafka) {

        if (embeddedKafka instanceof EmbeddedKafkaZKBroker zkBroker) {
            return () -> zkBroker.getKafkaServers().stream().allMatch(s -> BrokerState.RUNNING.equals(s.brokerState()));
        }

        return () -> false;
    }

}
