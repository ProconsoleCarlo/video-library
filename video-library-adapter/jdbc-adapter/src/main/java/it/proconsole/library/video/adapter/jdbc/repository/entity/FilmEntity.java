package it.proconsole.library.video.adapter.jdbc.repository.entity;

import it.proconsole.library.video.adapter.jdbc.model.Film;
import it.proconsole.library.video.adapter.jdbc.model.FilmReview;
import it.proconsole.library.video.adapter.jdbc.model.Genre;

import java.util.List;

public record FilmEntity(Long id, String title, Integer year) implements EntityWithId {
  public Film toDomain(List<Genre> genres, List<FilmReview> reviews) {
    return new Film(id, title, year, genres, reviews);
  }
}
