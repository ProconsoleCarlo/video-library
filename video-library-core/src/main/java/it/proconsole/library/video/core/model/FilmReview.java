package it.proconsole.library.video.core.model;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public record FilmReview(
        @Nullable Long id,
        LocalDateTime date,
        int rating,
        @Nullable String detail,
        @Nullable Long filmId
) {
  public FilmReview(LocalDateTime date, int rating, @Nullable String detail, Long filmId) {
    this(null, date, rating, detail, filmId);
  }

  public FilmReview(LocalDateTime date, int rating, @Nullable String detail) {
    this(null, date, rating, detail, null);
  }

  public Builder copy() {
    return new Builder(id, date, rating, detail, filmId);
  }

  public static class Builder {
    @Nullable
    private Long id;
    private LocalDateTime date;
    private int rating;
    @Nullable
    private String detail;
    @Nullable
    private Long filmId;

    Builder(@Nullable Long id, LocalDateTime date, int rating, @Nullable String detail, @Nullable Long filmId) {
      this.id = id;
      this.date = date;
      this.rating = rating;
      this.detail = detail;
      this.filmId = filmId;
    }

    Builder(LocalDateTime date, int rating) {
      this.date = date;
      this.rating = rating;
    }

    public static Builder aFilmReview(LocalDateTime date, int rating) {
      return new Builder(date, rating);
    }

    public Builder withId(@Nullable Long id) {
      this.id = id;
      return this;
    }

    public Builder withDate(LocalDateTime date) {
      this.date = date;
      return this;
    }

    public Builder withRating(int rating) {
      this.rating = rating;
      return this;
    }

    public Builder withDetail(@Nullable String detail) {
      this.detail = detail;
      return this;
    }

    public Builder withFilmId(@Nullable Long filmId) {
      this.filmId = filmId;
      return this;
    }

    public FilmReview build() {
      return new FilmReview(id, date, rating, detail, filmId);
    }
  }
}
