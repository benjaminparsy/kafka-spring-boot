SET REFERENTIAL_INTEGRITY FALSE;

TRUNCATE TABLE app.order_products;
TRUNCATE TABLE app.orders;
TRUNCATE TABLE app.user_projections;
TRUNCATE TABLE app.stock_projections;

TRUNCATE TABLE events.kafka_events;

SET REFERENTIAL_INTEGRITY TRUE;