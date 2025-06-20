-- H2
INSERT INTO generated_lottos (create_date, first_number, second_number, third_number, fourth_number, fifth_number, sixth_number)
VALUES
    (DATEADD(DAY, -9, CURRENT_TIMESTAMP), 3, 4, 6, 8, 32, 42),
    (DATEADD(DAY, -9, CURRENT_TIMESTAMP), 3, 10, 20, 30, 32, 42),
    (DATEADD(DAY, -9, CURRENT_TIMESTAMP), 3, 6, 8, 31, 32, 42),
    (DATEADD(DAY, -9, CURRENT_TIMESTAMP), 3, 4, 6, 31, 32, 42),
    (DATEADD(DAY, -9, CURRENT_TIMESTAMP), 3, 6, 8, 29, 32, 42),
    (DATEADD(DAY, -8, CURRENT_TIMESTAMP), 4, 6, 8, 32, 42, 45),
    (DATEADD(DAY, -8, CURRENT_TIMESTAMP), 6, 8, 17, 27, 32, 42),
    (DATEADD(DAY, -8, CURRENT_TIMESTAMP), 3, 4, 8, 23, 32, 39),
    (DATEADD(DAY, -8, CURRENT_TIMESTAMP), 3, 6, 8, 30, 36, 37),
    (DATEADD(DAY, -8, CURRENT_TIMESTAMP), 1, 8, 32, 42, 43, 44),
    (DATEADD(DAY, -7, CURRENT_TIMESTAMP), 10, 11, 14, 22, 36, 39),
    (DATEADD(DAY, -7, CURRENT_TIMESTAMP), 8, 11, 19, 36, 39, 45),
    (DATEADD(DAY, -7, CURRENT_TIMESTAMP), 8, 13, 20, 29, 36, 39),
    (DATEADD(DAY, -7, CURRENT_TIMESTAMP), 4, 6, 8, 10, 12, 14),
    (DATEADD(DAY, -7, CURRENT_TIMESTAMP), 5, 7, 9, 11, 13, 15),
    (DATEADD(DAY, -6, CURRENT_TIMESTAMP), 8, 9, 14, 22, 37, 39),
    (DATEADD(DAY, -6, CURRENT_TIMESTAMP), 2, 4, 6, 8, 10, 12),
    (DATEADD(DAY, -6, CURRENT_TIMESTAMP), 3, 5, 7, 9, 11, 13),
    (DATEADD(DAY, -6, CURRENT_TIMESTAMP), 4, 6, 8, 10, 12, 14),
    (DATEADD(DAY, -6, CURRENT_TIMESTAMP), 5, 7, 9, 11, 13, 15),
    (DATEADD(DAY, -5, CURRENT_TIMESTAMP), 3, 6, 7, 11, 12, 17),
    (DATEADD(DAY, -5, CURRENT_TIMESTAMP), 7, 9, 11, 13, 15, 17),
    (DATEADD(DAY, -5, CURRENT_TIMESTAMP), 8, 10, 12, 14, 16, 18),
    (DATEADD(DAY, -5, CURRENT_TIMESTAMP), 9, 11, 13, 15, 17, 19),
    (DATEADD(DAY, -5, CURRENT_TIMESTAMP), 10, 12, 14, 16, 18, 20),
    (DATEADD(DAY, -4, CURRENT_TIMESTAMP), 3, 6, 7, 11, 12, 17),
    (DATEADD(DAY, -4, CURRENT_TIMESTAMP), 12, 14, 16, 18, 20, 22),
    (DATEADD(DAY, -4, CURRENT_TIMESTAMP), 13, 15, 17, 19, 21, 23),
    (DATEADD(DAY, -4, CURRENT_TIMESTAMP), 14, 16, 18, 20, 22, 24),
    (DATEADD(DAY, -4, CURRENT_TIMESTAMP), 15, 17, 19, 21, 23, 25),
    (DATEADD(DAY, -3, CURRENT_TIMESTAMP), 3, 6, 7, 11, 12, 17),
    (DATEADD(DAY, -3, CURRENT_TIMESTAMP), 17, 19, 21, 23, 25, 27),
    (DATEADD(DAY, -3, CURRENT_TIMESTAMP), 18, 20, 22, 24, 26, 28),
    (DATEADD(DAY, -3, CURRENT_TIMESTAMP), 19, 21, 23, 25, 27, 29),
    (DATEADD(DAY, -3, CURRENT_TIMESTAMP), 20, 22, 24, 26, 28, 30),
    (DATEADD(DAY, -2, CURRENT_TIMESTAMP), 3, 6, 7, 11, 12, 17),
    (DATEADD(DAY, -2, CURRENT_TIMESTAMP), 20, 22, 24, 26, 28, 30),
    (DATEADD(DAY, -2, CURRENT_TIMESTAMP), 20, 22, 24, 26, 28, 30),
    (DATEADD(DAY, -2, CURRENT_TIMESTAMP), 20, 22, 24, 26, 28, 30),
    (DATEADD(DAY, -2, CURRENT_TIMESTAMP), 20, 22, 24, 26, 28, 30),
    (DATEADD(DAY, -1, CURRENT_TIMESTAMP), 3, 6, 7, 11, 12, 17),
    (DATEADD(DAY, -1, CURRENT_TIMESTAMP), 20, 22, 24, 26, 28, 30),
    (DATEADD(DAY, -1, CURRENT_TIMESTAMP), 20, 22, 24, 26, 28, 30),
    (DATEADD(DAY, -1, CURRENT_TIMESTAMP), 20, 22, 24, 26, 28, 30),
    (DATEADD(DAY, -1, CURRENT_TIMESTAMP), 20, 22, 24, 26, 28, 30);


-- Mysql
INSERT INTO generated_lottos (create_date, first_number, second_number, third_number, fourth_number, fifth_number, sixth_number)
VALUES
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 9 DAY), 3, 6, 7, 11, 12, 17),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 9 DAY), 3, 6, 7, 11, 12, 19),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 9 DAY), 6, 7, 11, 12, 17, 19),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 9 DAY), 3, 6, 7, 11, 17, 45),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 9 DAY), 5, 7, 9, 11, 13, 15),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 8 DAY), 3, 6, 7, 11, 12, 17),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 8 DAY), 2, 4, 6, 8, 10, 12),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 8 DAY), 3, 5, 7, 9, 11, 13),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 8 DAY), 4, 6, 8, 10, 12, 14),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 8 DAY), 5, 7, 9, 11, 13, 15),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 7 DAY), 3, 6, 7, 11, 12, 17),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 7 DAY), 2, 4, 6, 8, 10, 12),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 7 DAY), 3, 5, 7, 9, 11, 13),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 7 DAY), 4, 6, 8, 10, 12, 14),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 7 DAY), 5, 7, 9, 11, 13, 15),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 6 DAY), 3, 6, 7, 11, 12, 17),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 6 DAY), 2, 4, 6, 8, 10, 12),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 6 DAY), 3, 5, 7, 9, 11, 13),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 6 DAY), 4, 6, 8, 10, 12, 14),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 6 DAY), 5, 7, 9, 11, 13, 15),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 5 DAY), 3, 6, 7, 11, 12, 17),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 5 DAY), 7, 9, 11, 13, 15, 17),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 5 DAY), 8, 10, 12, 14, 16, 18),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 5 DAY), 9, 11, 13, 15, 17, 19),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 5 DAY), 10, 12, 14, 16, 18, 20),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 4 DAY), 3, 6, 7, 11, 12, 17),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 4 DAY), 12, 14, 16, 18, 20, 22),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 4 DAY), 13, 15, 17, 19, 21, 23),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 4 DAY), 14, 16, 18, 20, 22, 24),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 4 DAY), 15, 17, 19, 21, 23, 25),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 3 DAY), 3, 6, 7, 11, 12, 17),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 3 DAY), 17, 19, 21, 23, 25, 27),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 3 DAY), 18, 20, 22, 24, 26, 28),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 3 DAY), 19, 21, 23, 25, 27, 29),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 3 DAY), 20, 22, 24, 26, 28, 30),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 2 DAY), 3, 6, 7, 11, 12, 17),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 2 DAY), 20, 22, 24, 26, 28, 30),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 1 DAY), 3, 6, 7, 11, 12, 17),
    (DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 1 DAY), 20, 22, 24, 26, 28, 30);
