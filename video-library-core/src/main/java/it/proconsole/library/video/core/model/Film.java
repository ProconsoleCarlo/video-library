package it.proconsole.library.video.core.model;

import org.springframework.lang.Nullable;

import java.util.List;

public record Film(@Nullable Long id,
                   String title,
                   int year,
                   List<Genre> genres,
                   List<FilmReview> reviews
) {
  public Film(String title, int year, List<Genre> genres, List<FilmReview> reviews) {
    this(null, title, year, genres, reviews);
  }
}