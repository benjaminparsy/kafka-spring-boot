## Order microservice

- The Order microservice manages orders and contains information on users, products ordered and notifications sent.
- When an order is created, it publishes an OrderCreated event to Kafka. 
It also listens to **UserCreated**, **UserUpdated** and **StockUpdated** events to keep its local projections up to date.