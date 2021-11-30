package it.proconsole.library.video.adapter.jdbc.repository.entity;

import it.proconsole.library.video.adapter.jdbc.model.FilmReview;

import java.time.LocalDateTime;

public record FilmReviewEntity(long id, LocalDateTime date, int rating, String detail) {
  public FilmReview toDomain() {
    return new FilmReview(id, date, rating, detail);
  }
}
