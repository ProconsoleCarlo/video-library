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
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    var dbFilmReviews = dao.saveAll(List.of(aFilmReview, anotherFilmReview));

    var savedFilmReviews = ((FilmReviewDao) dao).findByFilmId(1L);
    var anotherSavedFilmReviews = ((FilmReviewDao) dao).findByFilmId(2L);

    assertEquals(List.of(dbFilmReviews.get(0)), savedFilmReviews);
    assertEquals(List.of(dbFilmReviews.get(1)), anotherSavedFilmReviews);

    dao.deleteAll(anotherSavedFilmReviews);
    jdbcTemplate.update("delete from film where id = 2");
  }

  @Test
  void saveByFilmId() {
    var date = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    var aFilmReview = new FilmReviewEntity(LocalDateTime.now(), 8, "Review", 1L);
    var anotherFilmReview = new FilmReviewEntity(date, 7, "Another review", 1L);
    var dbFilmReviews = dao.saveAll(List.of(aFilmReview, anotherFilmReview));

    var newFilmReview = new FilmReviewEntity(date, 9, "New review", 1L);

    var savedFilmReviews = ((FilmReviewDao) dao).saveByFilmId(List.of(dbFilmReviews.get(1), newFilmReview), 1L);

    var expected = List.of(
            dbFilmReviews.get(1),
            new FilmReviewEntity(3L, date, 9, "New review", 1L)
    );
    assertEquals(expected, savedFilmReviews);
  }
}