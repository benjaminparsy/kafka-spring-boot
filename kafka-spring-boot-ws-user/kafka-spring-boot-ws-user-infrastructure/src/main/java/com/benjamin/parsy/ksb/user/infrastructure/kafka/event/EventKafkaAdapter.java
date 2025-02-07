package com.benjamin.parsy.ksb.user.infrastructure.kafka.event;

/**
 * @param <E> Domain event class
 * @param <K> Kafka event class
 */
public interface EventKafkaAdapter<E, K> {

    K toKafkaEvent(E modelEvent);

}
