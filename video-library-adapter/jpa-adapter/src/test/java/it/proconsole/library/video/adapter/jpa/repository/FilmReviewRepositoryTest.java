package it.proconsole.library.video.adapter.jpa.repository;

import it.proconsole.library.video.adapter.jpa.repository.crud.FilmReviewCrudRepository;
import it.proconsole.library.video.core.model.FilmReview;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql({"/schema.sql", "/data.sql"})
class FilmReviewRepositoryTest {
  @Autowired
  private FilmReviewCrudRepository filmReviewCrudRepository;

  private FilmReviewRepository repository;

  @BeforeEach
  void setUp() {
    repository = new FilmReviewRepository(filmReviewCrudRepository);
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