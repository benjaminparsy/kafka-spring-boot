package com.benjamin.parsy.ksb.user.infrastructure.configuration;

public final class KafkaConstant {

    private KafkaConstant() {
        // Private constructor for utility class
    }

    // Producer topics
    public static final String TOPIC_USER_VALIDATED = "user-validated";
    public static final String TOPIC_ORDER_FAILED = "order-failed";

    // Consumer topics
    public static final String TOPIC_ORDER_CREATED = "order-created";

    // Group ID
    public static final String GROUP_ID_USER = "user-service";

}
