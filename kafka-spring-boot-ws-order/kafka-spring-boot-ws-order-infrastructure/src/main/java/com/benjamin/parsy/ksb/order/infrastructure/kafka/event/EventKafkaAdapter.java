package com.benjamin.parsy.ksb.order.infrastructure.kafka.event;

/**
 * @param <E> Event class
 * @param <K> Kafka event class
 */
public interface EventKafkaAdapter<E, K> {

    K toKafkaEvent(E modelEvent);

}
