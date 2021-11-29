package it.proconsole.library.video.adapter.jdbc.model;

import it.proconsole.library.video.core.model.Genre;

public record FilmGenreTable(long filmId, Genre genre) {
}
