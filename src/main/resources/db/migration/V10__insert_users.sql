SET @start_time = '2015-01-01 00:00:00';
SET @value = 60 * 60 * 24 * 365;

INSERT INTO users (is_moderator, reg_time, name, email, password)
VALUES (1, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Александр Вергун', 'altsora@amatera.su', '123456'),
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Герман Картер', 'german@shock.ru', 'abcdef'),
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Билл Гейтс', 'bill@gates.com', '654321'),
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Павел Дуров', 'durov@vk.ru', 'fedcba');