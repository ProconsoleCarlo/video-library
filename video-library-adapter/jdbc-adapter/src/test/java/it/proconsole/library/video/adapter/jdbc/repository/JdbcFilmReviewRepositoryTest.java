package it.proconsole.library.video.adapter.jdbc.repository;

import it.proconsole.library.video.adapter.jdbc.model.FilmEntity;
import it.proconsole.library.video.adapter.jdbc.repository.adapter.FilmReviewAdapter;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmReviewDao;
import it.proconsole.library.video.core.exception.FilmNotFoundException;
import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.repository.FilmReviewRepository;
import it.proconsole.library.video.core.repository.Protocol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@Sql("/schema.sql")
class JdbcFilmReviewRepositoryTest {
  @Autowired
  private DataSource dataSource;
  private FilmDao filmDao;

  private FilmReviewRepository repository;

  @BeforeEach
  void setUp() {
    filmDao = new FilmDao(dataSource);
    repository = new JdbcFilmReviewRepository(new FilmReviewDao(dataSource), filmDao, new FilmReviewAdapter());
  }

  @Test
  void protocol() {
    assertEquals(Protocol.JDBC, repository.protocol());
  }

  @Nested
  class WhenSave {
    @Test
    void save() {
      var film = filmDao.save(new FilmEntity("Title", 2018));
      assertNotNull(film.id());
      var review = new FilmReview(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), 8, "A review", film.id());

      var savedReview = repository.save(review);

      var expectedSavedReview = review.copy().withId(1L).build();
      assertEquals(expectedSavedReview, savedReview);

      var reviewToUpdate = expectedSavedReview.copy().withRating(7).withDetail("A review updated").build();

      var updatedReview = repository.save(reviewToUpdate);

      assertEquals(reviewToUpdate, updatedReview);
    }

    @ParameterizedTest
    @ValueSource(longs = Long.MAX_VALUE)
    @NullSource
    void notFoundExceptionWhenFilmDoesNotExist(Long filmId) {
      var review = new FilmReview(LocalDateTime.now(), 8, "Review", filmId);

      assertThrows(FilmNotFoundException.class, () -> repository.save(review));
    }
  }
}