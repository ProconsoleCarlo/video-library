package it.proconsole.library.video.adapter.jpa.repository;

import it.proconsole.library.video.adapter.jpa.repository.adapter.FilmReviewAdapter;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmReviewCrudRepository;
import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.repository.FilmReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql({"/schema.sql", "/dataOld.sql"})
class JpaFilmReviewRepositoryTest {
  @Autowired
  private FilmReviewCrudRepository filmReviewCrudRepository;

  private FilmReviewRepository repository;

  @BeforeEach
  void setUp() {
    repository = new JpaFilmReviewRepository(filmReviewCrudRepository, new FilmReviewAdapter());
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