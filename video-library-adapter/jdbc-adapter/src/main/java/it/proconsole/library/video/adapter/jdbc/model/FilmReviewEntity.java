package it.proconsole.library.video.adapter.jdbc.model;

import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public record FilmReviewEntity(
        @Nullable Long id,
        LocalDateTime date,
        Integer rating,
        @Nullable String detail,
        @Nullable Long filmId
) implements EntityWithId {
  public FilmReviewEntity(LocalDateTime date, int rating, @Nullable String detail, Long filmId) {
    this(null, date, rating, detail, filmId);
  }

  @Override
  public Map<String, Object> data() {
    return new HashMap<>() {{
      put("date", date.truncatedTo(ChronoUnit.MILLIS));
      put("rating", rating);
      put("detail", detail);
      put("film_id", filmId);
    }};
  }
}
