package it.proconsole.library.video.adapter.jdbc.repository.entity;

import it.proconsole.library.video.core.model.FilmReview;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public record FilmReviewEntity(
        @Nullable Long id,
        LocalDateTime date,
        Integer rating,
        @Nullable String detail,
        Long filmId
) implements EntityWithId {
  public FilmReviewEntity(LocalDateTime date, int rating, @Nullable String detail, Long filmId) {
    this(null, date, rating, detail, filmId);
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

  @Override
  public Map<String, ?> data() {
    return new HashMap<>() {{
      put("date", date);
      put("rating", rating);
      put("detail", detail);
      put("film_id", filmId);
    }};
  }
}
