package com.benjamin.parsy.ksb.order.application.kafka;

public final class KafkaConstant {


    private KafkaConstant() {
        // Private constructor for utility class
    }

    public static final String TOPIC_STOCK_RESERVED = "stock-reserved";
    public static final String TOPIC_ORDER_FAILED = "order-failed";

    // Group ID
    public static final String GROUP_ID_ORDER = "order-service";

}
