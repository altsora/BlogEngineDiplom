SET @start_time = '2017-01-01 00:00:00';
SET @value = 1;

INSERT INTO comments (parent_id, post_id, user_id, time, text)
VALUES (null, 1, 1, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_1'),
       (null, 1, 1, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_2'),
       (null, 1, 1, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_3'),
       (3, 1, 2, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_4'),
       (3, 1, 3, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_5'),
       (3, 1, 4, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_6'),
       (3, 1, 1, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_7'),
       (3, 1, 2, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_8'),
       (3, 1, 3, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_9'),
       (4, 1, 4, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_10'),

       (null, 4, 1, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_11'),
       (null, 4, 2, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_12'),
       (null, 4, 3, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_13'),
       (null, 4, 4, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_14'),
       (null, 4, 4, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_15'),

       (null, 7, 2, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_16'),
       (null, 7, 2, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_17'),
       (null, 7, 2, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_18'),
       (null, 7, 2, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_19'),

       (null, 8, 3, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_20'),
       (null, 8, 3, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_21'),
       (null, 8, 3, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_22'),

       (null, 9, 4, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_23'),
       (null, 9, 4, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_24'),

       (null, 10, 1, DATE_ADD(@start_time, INTERVAL (@value := @value + 1) DAY), 'Комментарий_25')