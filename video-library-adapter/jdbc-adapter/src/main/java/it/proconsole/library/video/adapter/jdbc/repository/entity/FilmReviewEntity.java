package it.proconsole.library.video.adapter.jdbc.repository.entity;

import it.proconsole.library.video.adapter.jdbc.model.FilmReview;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public record FilmReviewEntity(long id, LocalDateTime date, int rating, @Nullable String detail) {
  public FilmReview toDomain() {
    return new FilmReview(id, date, rating, detail);
  }
}
