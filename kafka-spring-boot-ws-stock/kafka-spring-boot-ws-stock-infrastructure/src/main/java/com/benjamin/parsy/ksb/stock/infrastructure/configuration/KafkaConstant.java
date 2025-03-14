package com.benjamin.parsy.ksb.stock.infrastructure.configuration;

public final class KafkaConstant {

    private KafkaConstant() {
        // Private constructor for utility class
    }

    // Group ID
    public static final String GROUP_ID_ORDER = "stock-service";

    public static class Producer {

        public static final String TOPIC_ORDER_FAILED = "order-failed";
        public static final String TOPIC_STOCK_RESERVED = "stock-reserved";

    }

    public static class Consumer {

        public static final String TOPIC_ORDER_CREATED = "order-created";

    }

}
