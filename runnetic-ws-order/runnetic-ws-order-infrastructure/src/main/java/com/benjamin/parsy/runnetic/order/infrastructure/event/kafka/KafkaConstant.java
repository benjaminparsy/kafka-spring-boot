package com.benjamin.parsy.runnetic.order.infrastructure.event.kafka;

public final class KafkaConstant {

    private KafkaConstant() {
        // Private constructor for utility class
    }

    // Group ID
    public static final String GROUP_ID_ORDER = "order-service";

    public static class Producer {

        public static final String TOPIC_ORDER_CREATED = "order-created";
        public static final String TOPIC_ORDER_CANCELED = "order-canceled";
        public static final String TOPIC_ORDER_CONFIRMED = "order-confirmed";

    }

    public static class Consumer {

        public static final String TOPIC_ORDER_FAILED = "order-failed";
        public static final String TOPIC_ORDER_PAID = "order-paid";

    }

}
