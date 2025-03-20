-- ================================================================
-- ORDERS
-- ================================================================
INSERT INTO app.orders(uuid, user_uuid, order_date, status, total_price)
VALUES ('56a99dec-d066-411f-9fa7-d525877c4117', '3030d90a-9523-4c93-8383-2dd9a857552d', now(), 'PENDING', 250.00);

INSERT INTO app.orders(uuid, user_uuid, order_date, status, total_price)
VALUES ('8b18a741-e009-4a25-ae6a-b1de9d1d406b', 'f338e92c-8119-4563-b168-56d870e76697', now(), 'CONFIRMED', 50.00);

-- ================================================================
-- ORDER_ITEMS
-- ================================================================
INSERT INTO app.order_items(product_uuid, quantity, price_at_order, order_id)
SELECT '3df2993c-a093-4606-9046-cdf8f84b872b', 2, 100.00, id FROM app.orders WHERE uuid = '56a99dec-d066-411f-9fa7-d525877c4117';

INSERT INTO app.order_items(product_uuid, quantity, price_at_order, order_id)
SELECT 'ff0baa97-49b8-4eab-98a1-7c70ccf93c85', 1, 50.00, id FROM app.orders WHERE uuid = '56a99dec-d066-411f-9fa7-d525877c4117';

INSERT INTO app.order_items(product_uuid, quantity, price_at_order, order_id)
SELECT '10ce4cb7-5e35-49c0-96ee-f24d7c708375', 1, 50.00, id FROM app.orders WHERE uuid = '8b18a741-e009-4a25-ae6a-b1de9d1d406b';