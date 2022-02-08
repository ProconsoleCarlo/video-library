package it.proconsole.library.video.adapter.xlsx.model;

import org.springframework.lang.Nullable;

import java.util.List;

public record FilmRow(
        @Nullable Long id,
        String title,
        Integer year,
        List<String> genres,
        List<FilmReviewRow> reviews
) {
  public FilmRow(String title, int year, List<String> genres, List<FilmReviewRow> reviews) {
    this(null, title, year, genres, reviews); //NOSONAR issue with record
  }
}
