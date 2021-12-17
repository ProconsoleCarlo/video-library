insert into `genre`
VALUES (1, 'ACTION'),
       (2, 'ADVENTURE'),
       (3, 'COMEDY'),
       (4, 'ROMANTIC'),
       (5, 'THRILLER');
insert into `film`
VALUES (1, '(S)ex list - What''s your number?', 2011);
insert into `film`
VALUES (2, '007 - 01 Licenza di uccidere', 1962);
insert into `film_genres`
VALUES (2, 1),
       (2, 2),
       (1, 3),
       (1, 4),
       (2, 5);
insert into `film_review`
VALUES (1, '2012-12-31 00:00:00', 7, NULL, 1),
       (2, '2013-10-18 00:00:00', 10, 'Molto bello', 2);