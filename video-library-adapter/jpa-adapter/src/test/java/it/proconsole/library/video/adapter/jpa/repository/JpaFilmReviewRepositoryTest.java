package it.proconsole.library.video.adapter.jpa.repository;

import it.proconsole.library.video.adapter.jpa.model.FilmEntity;
import it.proconsole.library.video.adapter.jpa.repository.adapter.FilmReviewAdapter;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmCrudRepository;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmReviewCrudRepository;
import it.proconsole.library.video.core.exception.FilmNotFoundException;
import it.proconsole.library.video.core.model.FilmReview;
import it.proconsole.library.video.core.repository.FilmReviewRepository;
import it.proconsole.library.video.core.repository.Protocol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Sql("/schema.sql")
class JpaFilmReviewRepositoryTest {
  @Autowired
  private FilmReviewCrudRepository filmReviewCrudRepository;
  @Autowired
  private FilmCrudRepository filmCrudRepository;

  private FilmReviewRepository repository;

  @BeforeEach
  void setUp() {
    repository = new JpaFilmReviewRepository(filmReviewCrudRepository, filmCrudRepository, new FilmReviewAdapter());
  }

  @Test
  void protocol() {
    assertEquals(Protocol.JPA, repository.protocol());
  }

  @Nested
  class WhenSave {
    @Test
    void save() {
      var film = filmCrudRepository.save(aFilm());
      var review = new FilmReview(LocalDateTime.now(), 8, "Review", film.getId());

      var currentSavedReview = repository.save(review);

      var savedReview = review.copy().withId(1L).build();
      assertEquals(savedReview, currentSavedReview);

      var reviewToUpdate = savedReview.copy().withRating(7).withDetail("Updated review").build();

      var updatedReview = repository.save(reviewToUpdate);

      assertEquals(reviewToUpdate, updatedReview);
    }

    @Test
    void notFoundExceptionWhenFilmDoesNotExist() {
      var review = new FilmReview(LocalDateTime.now(), 8, "Review", Long.MAX_VALUE);

      assertThrows(FilmNotFoundException.class, () -> repository.save(review));
    }
  }

  private FilmEntity aFilm() {
    var film = new FilmEntity();
    film.setTitle("Film title");
    film.setYear(2018);
    return film;
  }
}