SET @start_time = '2015-01-01 00:00:00';
SET @value = 60 * 60 * 24 * 365;

INSERT INTO users (is_moderator, reg_time, name, email, password)
VALUES (1, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Александр Вергун', 'altsora@amatera.su', '123456'),
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Герман Картер', 'german@shock.ru', 'abcdef'),
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Билл Гейтс', 'bill@gates.com', '654321'),
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Павел Дуров', 'durov@vk.ru', 'fedcba'),

       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'User 1', 'user_1@mail.ru', '123123'),
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'User 2', 'user_2@mail.ru', '123123'),
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'User 3', 'user_3@mail.ru', '123123'),
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'User 4', 'user_4@mail.ru', '123123'),
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'User 5', 'user_5@mail.ru', '123123'),
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'User 6', 'user_6@mail.ru', '123123'),
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'User 7', 'user_7@mail.ru', '123123'),
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'User 8', 'user_8@mail.ru', '123123'),
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'User 9', 'user_9@mail.ru', '123123');
