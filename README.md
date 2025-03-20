# Kafka with Spring Boot

This project is a sample application for learning and exploring the use of Kafka with Spring Boot. 
It provides a basic implementation and demonstrates how to configure, develop and test REST endpoints.

## Prerequisites

- [Java 17+](https://adoptopenjdk.net/)
- [Maven 3.8+](https://maven.apache.org/)

## Installation

1. Clone the project:

   ```bash
   git clone https://github.com/benjaminparsy/kafka-spring-boot
   ```

2. Compile and run the application:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## Use case context
Let's imagine an online store that needs to manage orders, stock products and send notifications to customers.
Each microservice is responsible for a specific function and sends events via Kafka to enable asynchronous processing and data analysis.

### Microservices involved

#### 1. Order Microservice:

- This service receives orders from users and sends them to Kafka.
- Events sent :
   - OrderCreated: When an order is created (containing information such as order ID, items ordered, user, etc.).
   - OrderCancelled: If the order is cancelled before shipment.

#### 2. Stock Microservice :

- This service listens to OrderCreated events to adjust stock according to ordered products.
- Events sent :
   - StockUpdated: When a product is added or removed from stock based on orders.

#### 3. Notifications Microservice :

- This service subscribes to order events to send notifications to the user (by email, SMS, etc.).
- Events sent :
   - NotificationSent: When notification is sent to the customer to inform them of the status of their order (created, cancelled, shipped, etc.).

#### 4. Analytics Microservice:

- This service subscribes to order and stock events to perform analyses (such as reports on popular products, sales trends, etc.).
- Events sent :
   - OrderSummary: Statistical calculations on sales and popular products.
   - StockStatus: Analysis of stock levels to predict out-of-stock situations.

### Event flows

#### 1. Order created :

- The user creates an order on the online store.
- The Order Microservice publishes an OrderCreated event in Kafka, containing the order details.

#### 2. Stock update:

- The Stock Microservice listens to OrderCreated events and updates the stock of ordered products.
- If a product's stock is modified, it sends a StockUpdated event to Kafka.

#### 3. User notification:

- The Notifications Microservice listens to OrderCreated events and sends a notification (email or SMS) to the user to confirm the order.
- When the notification is sent, a NotificationSent event is published in Kafka.

#### 4. Analytical processing :

- The Analytics Microservice listens to OrderCreated, StockUpdated and NotificationSent events to calculate performance metrics such as popular products, overall stock levels, etc. It can generate events such as OrderSent, StockUpdated and NotificationSent.
- It can generate events such as OrderSummary and StockStatus for real-time analysis or periodic reporting.

### Flow chart

<pre>

+-------------------+      +-------------------+      +-------------------+      +-------------------+
| Order             |      | Stock             |      | Notifications     |      | Analytics         |
| Microservice      |----->| Microservice      |----->| Microservice      |----->| Microservice      |
+-------------------+      +-------------------+      +-------------------+      +-------------------+
         |                          |                          |                          |
   [OrderCreated]             [StockUpdated]           [NotificationSent]      [OrderSummary/StockStatus]
</pre>

### Kafka Topics

- order-events : For events relating to orders (OrderCreated, OrderCancelled).
- stock-events : For events relating to stocks (StockUpdated).
- notification-events : For events relating to notifications sent (NotificationSent).
- analytics-events : For events relating to order and stock analyses (OrderSummary, StockStatus).

### Technical implementation

- Kafka Producers: Each microservice acts as a Kafka producer, sending messages to specific topics.
- Kafka Consumers: Microservices listen to specific topics and react according to the events received (updating stock, sending notifications, analytical processing).

### Key learning points

1. Asynchronous event management: Kafka enables asynchronous communication between microservices, where each service can process events at its own pace.
2. Scalability: Kafka enables large numbers of events to be processed in parallel, which is essential for large-scale applications.
3. Service decoupling: Each microservice can evolve independently, as they communicate only via Kafka events.
4. Real-time analysis: Kafka lets you process events in real time to feed analytical systems or dashboards.

This simple use case will enable you to explore the basics of Kafka, understand how microservices communicate
via events and experiment with asynchronous and analytical processing scenarios.
