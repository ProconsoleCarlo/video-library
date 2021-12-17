package it.proconsole.library.video.adapter.jdbc.repository.entity;

import it.proconsole.library.video.adapter.jdbc.model.FilmReview;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public record FilmReviewEntity(
        Long id,
        LocalDateTime date,
        Integer rating,
        @Nullable String detail,
        Long filmId
) implements EntityWithId {
  public FilmReviewEntity(LocalDateTime date, int rating, @Nullable String detail, Long filmId) {
    this(1L, date, rating, detail, filmId);
  }

  public static FilmReviewEntity fromDomain(FilmReview filmReview) {
    return new FilmReviewEntity(
            filmReview.id(),
            filmReview.date(),
            filmReview.rating(),
            filmReview.detail(),
            filmReview.filmId()
    );
  }

  public FilmReview toDomain() {
    return new FilmReview(id, date, rating, detail, filmId);
  }
}
