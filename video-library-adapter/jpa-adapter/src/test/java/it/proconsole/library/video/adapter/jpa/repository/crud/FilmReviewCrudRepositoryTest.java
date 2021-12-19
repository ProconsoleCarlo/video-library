package it.proconsole.library.video.adapter.jpa.repository.crud;

import it.proconsole.library.video.adapter.jpa.model.FilmReviewEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Sql({"/schema.sql", "/data.sql"})
class FilmReviewCrudRepositoryTest {
  @Autowired
  private FilmReviewCrudRepository filmReviewRepository;

  @Test
  void findByFilmId() {
    var filmId = 1L;
    var review = new FilmReviewEntity(1L, LocalDateTime.now(), 8, "This is a review", filmId);

    var savedReview = filmReviewRepository.save(review);

    assertTrue(filmReviewRepository.findByFilmId(filmId).contains(savedReview));
  }
}