package it.proconsole.library.video.adapter.jdbc.repository;

import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmReviewDao;
import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.repository.FilmReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Sql({"/schema.sql", "/data.sql"})
class JdbcFilmReviewRepositoryTest {
  @Autowired
  private DataSource dataSource;

  private FilmReviewRepository repository;

  @BeforeEach
  void setUp() {
    repository = new JdbcFilmReviewRepository(new FilmReviewDao(dataSource));
  }

  @Test
  void save() {
    var review = new FilmReview(1L, LocalDateTime.now(), 8, "A review", 1L);

    var savedReview = repository.save(review);

    assertEquals(review, savedReview);

    var reviewToUpdate = new FilmReview(1L, LocalDateTime.now(), 8, "A review updated", 1L);

    var updatedReview = repository.save(reviewToUpdate);

    assertEquals(reviewToUpdate, updatedReview);
  }
}