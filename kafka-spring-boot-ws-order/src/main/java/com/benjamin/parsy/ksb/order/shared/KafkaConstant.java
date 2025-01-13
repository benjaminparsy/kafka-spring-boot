package com.benjamin.parsy.ksb.order.shared;

public final class KafkaConstant {

    private KafkaConstant() {
        // Private constructor for utility class
    }

    // Producer topics
    public static final String TOPIC_ORDER_CREATED = "order-created";

    // Consumer topics
    public static final String TOPIC_USER_CREATED = "user-created";
    public static final String TOPIC_USER_UPDATED = "user-updated";
    public static final String TOPIC_STOCK_UPDATED = "stock-updated";

    // Group ID
    public static final String GROUP_ID_USER = "user-service";
    public static final String GROUP_ID_STOCK = "stock-service";

}
