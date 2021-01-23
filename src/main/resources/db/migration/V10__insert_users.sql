SET @start_time = '2015-01-01 00:00:00';
SET @value = 60 * 60 * 24 * 365;

INSERT INTO users (is_moderator, reg_time, name, email, password)
VALUES (1, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Александр Вергун', 'altsora@amatera.su', '$2y$12$TbGp0X68kaMBZMAn.KxHCuCPvwJ8eOD60CAJc5UPABqo88njhJViy'), -- 123456
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Герман Картер', 'german@shock.ru', '$2y$12$am29FzykF2xdOQeMqVc16uU/KshF.mnt.KA1Vp3.S6pky8VCHCjYK'),    -- abcdef
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Билл Гейтс', 'bill@gates.com', '$2y$12$VNPq9eMvVNDYNGDJqkUvPOdkskfeXvoVPWrWv0Bv.9/zNWq1WsQTW'),    -- 654321
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'Павел Дуров', 'durov@vk.ru', '$2y$12$VbPy3UNNIj5RdMQhM4z2z.TPJjn8lSXC5m9U3OpyrfOLsqB0JWK7G'),  -- fedcba

       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'User 1', 'user_1@mail.ru', '$2y$12$qQb5QLt2Eck90e5O0Z5TG.RVuZWGndULYqCFBGa8BKpTL1d0UtFgy'),    -- 123123
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'User 2', 'user_2@mail.ru', '$2y$12$qQb5QLt2Eck90e5O0Z5TG.RVuZWGndULYqCFBGa8BKpTL1d0UtFgy'),    -- 123123
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'User 3', 'user_3@mail.ru', '$2y$12$qQb5QLt2Eck90e5O0Z5TG.RVuZWGndULYqCFBGa8BKpTL1d0UtFgy'),    -- 123123
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'User 4', 'user_4@mail.ru', '$2y$12$qQb5QLt2Eck90e5O0Z5TG.RVuZWGndULYqCFBGa8BKpTL1d0UtFgy'),    -- 123123
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'User 5', 'user_5@mail.ru', '$2y$12$qQb5QLt2Eck90e5O0Z5TG.RVuZWGndULYqCFBGa8BKpTL1d0UtFgy'),    -- 123123
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'User 6', 'user_6@mail.ru', '$2y$12$qQb5QLt2Eck90e5O0Z5TG.RVuZWGndULYqCFBGa8BKpTL1d0UtFgy'),    -- 123123
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'User 7', 'user_7@mail.ru', '$2y$12$qQb5QLt2Eck90e5O0Z5TG.RVuZWGndULYqCFBGa8BKpTL1d0UtFgy'),    -- 123123
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'User 8', 'user_8@mail.ru', '$2y$12$qQb5QLt2Eck90e5O0Z5TG.RVuZWGndULYqCFBGa8BKpTL1d0UtFgy'),    -- 123123
       (0, DATE_ADD(@start_time, INTERVAL FLOOR(RAND() * @value) SECOND), 'User 9', 'user_9@mail.ru', '$2y$12$qQb5QLt2Eck90e5O0Z5TG.RVuZWGndULYqCFBGa8BKpTL1d0UtFgy');    -- 123123
