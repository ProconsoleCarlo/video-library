package it.proconsole.library.video.adapter.jpa.repository.crud;

import it.proconsole.library.video.adapter.jpa.model.Film;
import it.proconsole.library.video.adapter.jpa.model.FilmReviewEntity;
import it.proconsole.library.video.core.Fixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Sql({"/schema.sql", "/data.sql"})
class FilmReviewRepositoryTest {
  private static final String FILMS_JSON = "/it/proconsole/library/video/core/model/films.json";

  @Autowired
  private FilmReviewRepository filmReviewRepository;

  @Test
  void findAll() {
    var film = Fixtures.readListFromClasspath(FILMS_JSON, Film.class).get(0);
    var review = new FilmReviewEntity(LocalDateTime.now(), 8, "This is a review", film.getId());

    var savedReview = filmReviewRepository.save(review);

    assertTrue(filmReviewRepository.findByFilmId(film.getId()).contains(savedReview));
  }
}