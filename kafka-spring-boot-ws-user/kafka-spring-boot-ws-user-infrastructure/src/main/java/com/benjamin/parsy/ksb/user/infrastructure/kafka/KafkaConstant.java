package com.benjamin.parsy.ksb.user.infrastructure.kafka;

public final class KafkaConstant {

    private KafkaConstant() {
        // Private constructor for utility class
    }

    // Producer topics
    public static final String TOPIC_USER_VALIDATED = "user-validated";
    public static final String TOPIC_ORDER_FAILED = "order-failed";

}
