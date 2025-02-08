package com.benjamin.parsy.ksb.user.application.kafka;

public final class KafkaConstant {


    private KafkaConstant() {
        // Private constructor for utility class
    }

    // Topics
    public static final String TOPIC_ORDER_CREATED = "order-created";

    // Group ID
    public static final String GROUP_ID_USER = "user-service";

}
