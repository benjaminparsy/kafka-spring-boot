package com.benjamin.parsy.ksb.order.infrastructure.configuration;

public final class KafkaConstant {

    private KafkaConstant() {
        // Private constructor for utility class
    }

    // Producer topics
    public static final String TOPIC_ORDER_CREATED = "order-created";
    public static final String TOPIC_ORDER_CANCELED = "order-canceled";
    public static final String TOPIC_ORDER_CONFIRMED = "order-confirmed";

    // Consumer topics
    public static final String TOPIC_ORDER_FAILED = "order-failed";
    public static final String TOPIC_ORDER_PAID = "order-paid";

    // Group ID
    public static final String GROUP_ID_ORDER = "order-service";

}
