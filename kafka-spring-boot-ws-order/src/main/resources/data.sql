-- ================================================================
-- USER_PROJECTION
-- ================================================================
INSERT INTO app.user_projections(name, email, address, phone)
VALUES ('Benjamin Parsy', 'benjamin.parsy@example.com', 'N°4 main road, New-York', '0123456789');

INSERT INTO app.user_projections(name, email, address, phone)
VALUES ('Nils Mercier', 'nils.mercier@example.com', '3055 Rue Bataille, Avignon', '0404542641');

INSERT INTO app.user_projections(name, email, address, phone)
VALUES ('Elliot Gauthier', 'elliot.gauthier@example.com', '3055 Grand Ave, Georgetown', 'U78 L60-9295');

-- ================================================================
-- ORDERS
-- ================================================================
INSERT INTO app.orders(order_date, order_status, order_total, user_projection_id)
SELECT now(), 'CREATED', 124.98, id FROM app.user_projections WHERE email = 'benjamin.parsy@example.com';

INSERT INTO app.orders(order_date, order_status, order_total, user_projection_id)
SELECT now(), 'PROCESSING', 4687.34, id FROM app.user_projections WHERE email = 'elliot.gauthier@example.com';

-- ================================================================
-- STOCK_PROJECTION
-- ================================================================
INSERT INTO app.stock_projections(product_id, product_name, price, stock_quantity)
VALUES (23, 'hoover', 100.00, 10);

INSERT INTO app.stock_projections(product_id, product_name, price, stock_quantity)
VALUES (34, 'iron', 50.00, 8);