INSERT INTO entity(name) VALUES ('1');
INSERT INTO entity(name) VALUES ('2');

INSERT INTO sub_entity(name, entity_id) SELECT '1', id from entity where name = '1';
INSERT INTO sub_entity(name, entity_id) SELECT '2', id from entity where name = '2';