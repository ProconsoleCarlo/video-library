package it.proconsole.library.video.adapter.xlsx.model;

import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.List;

public record FilmRow(
        @Nullable Long id,
        String title,
        Integer year,
        List<String> genres,
        List<FilmReviewRow> reviews
) {
  public Builder copy() {
    return new Builder(this);
  }

  public static class Builder {
    @Nullable
    private Long id;
    private String title;
    private Integer year;
    private List<String> genres;
    private List<FilmReviewRow> reviews;

    Builder(FilmRow filmRow) {
      this.id = filmRow.id;
      this.title = filmRow.title;
      this.year = filmRow.year;
      this.genres = filmRow.genres;
      this.reviews = filmRow.reviews;
    }

    private Builder(String title, Integer year) {
      this.title = title;
      this.year = year;
      this.genres = Collections.emptyList();
      this.reviews = Collections.emptyList();
    }

    public static Builder aFilmRow(String title, Integer year) {
      return new Builder(title, year);
    }

    public Builder withId(@Nullable Long id) {
      this.id = id;
      return this;
    }

    public Builder withTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder withYear(Integer year) {
      this.year = year;
      return this;
    }

    public Builder withGenres(List<String> genres) {
      this.genres = genres;
      return this;
    }

    public Builder withReviews(List<FilmReviewRow> reviews) {
      this.reviews = reviews;
      return this;
    }

    public FilmRow build() {
      return new FilmRow(id, title, year, genres, reviews);
    }
  }
}
