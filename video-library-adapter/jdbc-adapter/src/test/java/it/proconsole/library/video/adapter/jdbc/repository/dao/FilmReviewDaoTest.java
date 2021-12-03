package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.ApplicationConfig;
import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmReviewEntity;
import it.proconsole.library.video.core.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ApplicationConfig.class)
@Profile("test")
class FilmReviewDaoTest {
  private static final String FILM_REVIEWS_JSON = "/it/proconsole/library/video/adapter/model/filmReviews.json";

  @Autowired
  private DataSource dataSource;

  private FilmReviewDao dao;

  @BeforeEach
  void setUp() {
    dao = new FilmReviewDao(dataSource);
  }

  @Test
  void findAll() {
    var current = dao.findAll();
    var expected = Fixtures.readListFromClasspath(FILM_REVIEWS_JSON, FilmReviewEntity.class);

    assertEquals(expected, current);
  }

  @Nested
  class WhenFindByFilmId {
    @Test
    void listIfPresent() {
      var current = dao.findByFilmId(1);
      var expected = List.of(
              new FilmReviewEntity(1, LocalDateTime.of(2012, Month.DECEMBER, 31, 0, 0), 7, null)
      );

      assertEquals(expected, current);
    }

    @Test
    void emptyListIfAbsent() {
      var current = dao.findByFilmId(10);

      assertTrue(current.isEmpty());
    }
  }
}