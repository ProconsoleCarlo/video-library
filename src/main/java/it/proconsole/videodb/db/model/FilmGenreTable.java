package it.proconsole.videodb.db.model;

import it.proconsole.videodb.core.model.Genre;

public record FilmGenreTable(long filmId, Genre genre) {
}
