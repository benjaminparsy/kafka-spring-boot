package com.benjamin.parsy.runnetic.analytics.kafka;

import com.benjamin.parsy.runnetic.analytics.document.EventLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaEventListener {

    private final ElasticsearchOperations elasticsearchOperations;

    @KafkaListener(topics = {
            "${kafka.topics.consumer.order-created}",
            "${kafka.topics.consumer.order-failed}",
            "${kafka.topics.consumer.order-canceled}",
            "${kafka.topics.consumer.order-confirmed}",
            "${kafka.topics.consumer.order-paid}"
    }, groupId = "${spring.kafka.consumer.group-id}")
    public void listen(ConsumerRecord<String, GenericRecord> message) {

        Map<String, String> payloadMap = message.value().getSchema()
                .getFields()
                .stream()
                .collect(Collectors.toMap(
                        Schema.Field::name,
                        f -> String.valueOf(message.value().get(f.name())))
                );

        EventLog eventLog = new EventLog();
        eventLog.setTimestamp(Instant.now());
        eventLog.setEventType(message.key());
        eventLog.setEventSource(message.topic());
        eventLog.setPayload(Map.of("avroData", payloadMap));
        eventLog.setPartition(message.partition());
        eventLog.setOffset(message.offset());

        // Indexer dans Elasticsearch
        elasticsearchOperations.save(eventLog);

        log.info("Event indexed successfully: {}", eventLog);
    }

}
