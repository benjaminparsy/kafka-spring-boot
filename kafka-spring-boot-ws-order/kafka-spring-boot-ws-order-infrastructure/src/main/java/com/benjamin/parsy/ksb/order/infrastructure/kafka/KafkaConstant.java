package com.benjamin.parsy.ksb.order.infrastructure.kafka;

public final class KafkaConstant {

    private KafkaConstant() {
        // Private constructor for utility class
    }

    // Producer topics
    public static final String TOPIC_ORDER_CREATED = "order-created";
    public static final String TOPIC_ORDER_CANCELED = "order-canceled";
    public static final String TOPIC_ORDER_CONFIRMED = "order-confirmed";

}
