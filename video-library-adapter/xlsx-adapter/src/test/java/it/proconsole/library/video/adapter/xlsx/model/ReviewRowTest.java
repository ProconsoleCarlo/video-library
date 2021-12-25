package it.proconsole.library.video.adapter.xlsx.model;

import it.proconsole.library.video.core.model.FilmReview;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReviewRowTest {
  private static final LocalDateTime DATE = LocalDateTime.now();
  private static final Long FILM_ID = 1L;
  private static final FilmReview DOMAIN = new FilmReview(1L, DATE, 10, "Review", FILM_ID);
  private static final ReviewRow FRONTIER = new ReviewRow(1L, DATE, 10, "Review");

  @Test
  void fromDomain() {
    var current = ReviewRow.fromDomain(DOMAIN);

    assertEquals(FRONTIER, current);
  }

  @Test
  void toDomain() {
    var current = FRONTIER.toDomain(FILM_ID);

    assertEquals(DOMAIN, current);
  }
}