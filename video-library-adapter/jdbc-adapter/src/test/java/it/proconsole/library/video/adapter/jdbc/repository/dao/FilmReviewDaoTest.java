package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.jdbc.model.FilmReviewEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Sql({"/schema.sql"})
class FilmReviewDaoTest extends DatabaseDaoTest<FilmReviewEntity> {
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private DataSource dataSource;

  @BeforeEach
  void setUp() {
    jdbcTemplate = new JdbcTemplate(dataSource);
    dao = new FilmReviewDao(dataSource);

    jdbcTemplate.update("insert into film VALUES (1, 'Film title', 2011);");
  }

  @Override
  @AfterEach
  void tearDown() {
    super.tearDown();
    jdbcTemplate.update("delete from film where id = 1");
  }

  @Override
  FilmReviewEntity anEntity() {
    return new FilmReviewEntity(LocalDateTime.of(2012, Month.DECEMBER, 31, 0, 0), 7, "This is a review", 1L);
  }

  @Override
  FilmReviewEntity anEntityForUpdate(Long id) {
    return new FilmReviewEntity(id, LocalDateTime.of(2012, Month.DECEMBER, 31, 0, 0), 7, "Updated review", 1L);
  }

  @Test
  void findByFilmId() {
    jdbcTemplate.update("insert into film VALUES (2, 'Another film title', 2012);");
    var aFilmReview = new FilmReviewEntity(LocalDateTime.now(), 8, "Review", 1L);
    var anotherFilmReview = new FilmReviewEntity(LocalDateTime.now(), 7, "Another review", 2L);
    var savedFilmReviews = dao.saveAll(List.of(aFilmReview, anotherFilmReview));

    var savedFilmReview = dao.findById(1L);
    var anotherSavedFilmReview = dao.findById(2L);

    assertTrue(savedFilmReview.isPresent());
    assertEquals(savedFilmReviews.get(0), savedFilmReview.get());
    assertTrue(anotherSavedFilmReview.isPresent());
    assertEquals(savedFilmReviews.get(1), anotherSavedFilmReview.get());

    dao.delete(anotherSavedFilmReview.get());
    jdbcTemplate.update("delete from film where id = 2");
  }
}