drop table IF EXISTS `film_genres`;
drop table IF EXISTS `film_review`;
drop table IF EXISTS `film`;
drop table IF EXISTS `genre`;

create TABLE `genre`
(
    `id`    int          NOT NULL AUTO_INCREMENT,
    `value` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);

create TABLE `film`
(
    `id`    int          NOT NULL AUTO_INCREMENT,
    `title` varchar(255) NOT NULL,
    `year`  year         NOT NULL,
    PRIMARY KEY (`id`)
);

create TABLE `film_genres`
(
    `film_id`  int NOT NULL,
    `genre_id` int NOT NULL,
    PRIMARY KEY (`film_id`, `genre_id`),
    CONSTRAINT `fk_film_genre_film1` FOREIGN KEY (`film_id`) REFERENCES `film` (`id`),
    CONSTRAINT `fk_film_genre_genre1` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`)
);

create TABLE `film_review`
(
    `id`      int      NOT NULL AUTO_INCREMENT,
    `date`    datetime NOT NULL,
    `rating`  tinyint  NOT NULL,
    `detail`  text,
    `film_id` int      NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_film_review_film1` FOREIGN KEY (`film_id`) REFERENCES `film` (`id`)
);