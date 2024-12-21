-- ================================================================
-- AUTHORS
-- ================================================================
INSERT INTO author (firstname, lastname) VALUES ('Albert', 'Camus');
INSERT INTO author (firstname, lastname) VALUES ('Victor', 'Hugo');

-- ================================================================
-- BOOKS
-- ================================================================
INSERT INTO book (title, category, created_date, author_id)
SELECT 'L''Etranger', 'Novel', '2024-12-17', id FROM author a WHERE a.lastname = 'Camus';

INSERT INTO book (title, category, created_date, author_id)
SELECT 'La Chute', 'Novel', '2024-12-18', id FROM author a WHERE a.lastname = 'Camus';

INSERT INTO book (title, category, created_date, author_id)
SELECT 'Notre-Dame de Paris', 'Novel', '2024-12-19', id FROM author a WHERE a.lastname = 'Hugo';

INSERT INTO book (title, category, created_date, author_id)
SELECT 'Les Misérables', 'Novel', '2024-12-20', id FROM author a WHERE a.lastname = 'Hugo';

-- ================================================================
-- REVIEWS
-- ================================================================
INSERT INTO review (text, created_by, book_id)
SELECT 'Very interesting.', 'Benjamin', id FROM book b WHERE b.title = 'L''Etranger';

INSERT INTO review (text, created_by, book_id)
SELECT 'Good book!', 'John', id FROM book b WHERE b.title = 'Les Misérables';