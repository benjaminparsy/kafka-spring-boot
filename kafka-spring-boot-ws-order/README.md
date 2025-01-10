## Order microservice

- The Order microservice manages orders and contains information on users, products ordered and notifications sent.
- When an order is created, it publishes an **order-created** event to Kafka. 
It also listens to **user-created**, **user-updated** and **stock-updated** events to keep its local projections up to date.