SET @start_time = '2016-01-01 00:00:00';
SET @value = 60 * 60 * 24 * 365;
SET @A = 'ACCEPTED';
SET @D = 'DECLINED';
SET @N = 'NEW';
SET @MODER_ID = 1;

INSERT INTO posts (activity_status, moderation_status, moderator_id, user_id, time, title, text, view_count)
VALUES ('ACTIVE', @A, NULL, 1, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Шинра Тенсей', 'Самый лучший текст', 10),
       ('ACTIVE', @N, NULL, 2, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Супер шок!', 'Меня зовут Герман Картер!', 0),
       ('INACTIVE', @N, NULL, 3, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Шиндоус', 'Время переустанавливать шиндооус!', 0),
       ('ACTIVE', @A, @MODER_ID, 3, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Чипирование', 'Чип Чип Чип Чип Чип и Дейл к вам спешат...', 1),
       ('ACTIVE', @D, @MODER_ID, 3, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Вышка 5G', 'Известна вам как Выжигатель Мозгов', 0),
       ('ACTIVE', @D, @MODER_ID, 3, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Вышка 5G, Версия 2', 'Продолжение истории про вышки!', 0),
       ('ACTIVE', @A, @MODER_ID, 4, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Тема_1', 'Текст_1', 9),
       ('ACTIVE', @A, @MODER_ID, 4, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Тема_2', 'Текст_2', 8),
       ('ACTIVE', @A, @MODER_ID, 4, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Тема_3', 'Текст_3', 7),
       ('ACTIVE', @A, @MODER_ID, 4, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Тема_4', 'Текст_4', 6),
       ('ACTIVE', @A, @MODER_ID, 4, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Тема_5', 'Текст_5', 5),
       ('ACTIVE', @A, @MODER_ID, 4, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Тема_6', 'Текст_6', 4),
       ('ACTIVE', @A, @MODER_ID, 4, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Тема_7', 'Текст_7', 3),
       ('ACTIVE', @A, @MODER_ID, 4, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Тема_8', 'Текст_8', 2),
       ('ACTIVE', @A, @MODER_ID, 4, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Тема_9', 'Текст_9', 1)