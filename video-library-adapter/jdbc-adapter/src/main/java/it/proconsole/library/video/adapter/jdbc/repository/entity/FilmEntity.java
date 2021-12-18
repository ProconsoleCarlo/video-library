package it.proconsole.library.video.adapter.jdbc.repository.entity;

import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.model.Genre;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

public record FilmEntity(@Nullable Long id, String title, Integer year) implements EntityWithId {
  public FilmEntity(String title, Integer year) {
    this(null, title, year);
  }

  public static FilmEntity fromDomain(Film film) {
    return new FilmEntity(film.id(), film.title(), film.year());
  }

  public Film toDomain(List<Genre> genres, List<FilmReview> reviews) {
    return new Film(id, title, year, genres, reviews);
  }

  @Override
  public Map<String, ?> data() {
    return Map.of(
            "title", title,
            "year", year
    );
  }
}
