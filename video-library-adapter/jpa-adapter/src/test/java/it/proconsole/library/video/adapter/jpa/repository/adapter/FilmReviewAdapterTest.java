package it.proconsole.library.video.adapter.jpa.repository.adapter;

import it.proconsole.library.video.adapter.jpa.model.FilmEntity;
import it.proconsole.library.video.adapter.jpa.model.FilmReviewEntity;
import it.proconsole.library.video.core.model.FilmReview;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilmReviewAdapterTest {
  private static final LocalDateTime DATE = LocalDateTime.now();
  private static final FilmEntity FILM = new FilmEntity() {{
    setId(1L);
  }};
  private static final List<FilmReview> DOMAIN = List.of(
          new FilmReview(1L, DATE, 10, "Review", FILM.getId()),
          new FilmReview(2L, DATE, 6, null, FILM.getId())
  );
  private static final List<FilmReviewEntity> FRONTIER = List.of(
          new FilmReviewEntity(1L, DATE, 10, "Review", FILM),
          new FilmReviewEntity(2L, DATE, 6, null, FILM)
  );

  private final FilmReviewAdapter adapter = new FilmReviewAdapter();

  @Test
  void fromDomain() {
    var current = adapter.fromDomain(DOMAIN, FILM);

    assertEquals(FRONTIER, current);
  }

  @Test
  void toDomain() {
    var current = adapter.toDomain(FRONTIER);

    assertEquals(DOMAIN, current);
  }
}