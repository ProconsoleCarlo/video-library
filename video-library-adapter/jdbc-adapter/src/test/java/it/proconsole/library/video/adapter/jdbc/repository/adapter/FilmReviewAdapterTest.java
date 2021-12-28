package it.proconsole.library.video.adapter.jdbc.repository.adapter;

import it.proconsole.library.video.adapter.jdbc.model.FilmReviewEntity;
import it.proconsole.library.video.core.model.FilmReview;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilmReviewAdapterTest {
  private static final LocalDateTime DATE = LocalDateTime.now();
  private static final Long FILM_ID = 1L;
  private static final List<FilmReview> DOMAIN = List.of(
          new FilmReview(1L, DATE, 10, "Review", FILM_ID),
          new FilmReview(2L, DATE, 6, null, FILM_ID)
  );
  private static final List<FilmReviewEntity> FRONTIER = List.of(
          new FilmReviewEntity(1L, DATE, 10, "Review", FILM_ID),
          new FilmReviewEntity(2L, DATE, 6, null, FILM_ID)
  );

  private final FilmReviewAdapter adapter = new FilmReviewAdapter();

  @Test
  void fromDomain() {
    var current = adapter.fromDomain(DOMAIN, FILM_ID);

    assertEquals(FRONTIER, current);
  }

  @Test
  void toDomain() {
    var current = adapter.toDomain(FRONTIER);

    assertEquals(DOMAIN, current);
  }
}