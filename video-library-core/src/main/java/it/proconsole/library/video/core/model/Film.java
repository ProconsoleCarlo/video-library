package it.proconsole.library.video.core.model;

import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public record Film(@Nullable Long id,
                   String title,
                   int year,
                   List<Genre> genres,
                   List<FilmReview> reviews
) {
  public Film(String title, int year, List<GenreEnum> genres, List<FilmReview> reviews) {
    this(null, title, year, genres.stream().map(it -> new Genre(it)).toList(), reviews); //NOSONAR issue with record
  }

  public Builder copy() {
    return new Builder(id, title, year, genres, reviews);
  }

  public static class Builder {
    @Nullable
    private Long id;
    private String title;
    private int year;
    private List<Genre> genres = Collections.emptyList();
    private List<FilmReview> reviews = Collections.emptyList();

    Builder(@Nullable Long id, String title, int year, List<Genre> genres, List<FilmReview> reviews) {
      this.id = id;
      this.title = title;
      this.year = year;
      this.genres = genres;
      this.reviews = reviews;
    }

    private Builder(String title, int year) {
      this.title = title;
      this.year = year;
    }

    public static Builder aFilm(String title, int year) {
      return new Builder(title, year);
    }

    public Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public Builder withTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder withYear(int year) {
      this.year = year;
      return this;
    }

    public Builder withGenres(Genre... genres) {
      this.genres = Arrays.asList(genres);
      return this;
    }

    public Builder withGenres(GenreEnum... genres) {
      this.genres = Arrays.stream(genres).map(it -> new Genre(it.id(), it)).toList();
      return this;
    }

    public Builder withGenres(List<Genre> genres) {
      this.genres = genres;
      return this;
    }

    public Builder withReviews(FilmReview... reviews) {
      this.reviews = Arrays.asList(reviews);
      return this;
    }

    public Builder withReviews(List<FilmReview> reviews) {
      this.reviews = reviews;
      return this;
    }

    public Film build() {
      return new Film(id, title, year, genres, reviews);
    }
  }
}