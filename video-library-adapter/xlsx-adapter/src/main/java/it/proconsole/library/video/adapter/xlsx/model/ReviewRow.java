package it.proconsole.library.video.adapter.xlsx.model;

import it.proconsole.library.video.core.model.FilmReview;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public record ReviewRow(
        Long id,
        LocalDateTime date,
        Integer rating,
        @Nullable String detail
) {
  public static ReviewRow fromDomain(FilmReview filmReview) {
    return new ReviewRow(filmReview.id(), filmReview.date(), filmReview.rating(), filmReview.detail());
  }

  public FilmReview toDomain(Long filmId) {
    return new FilmReview(id, date, rating, detail, filmId);
  }
}
