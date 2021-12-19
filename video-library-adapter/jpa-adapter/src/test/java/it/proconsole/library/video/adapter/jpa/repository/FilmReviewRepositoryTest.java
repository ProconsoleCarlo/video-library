package it.proconsole.library.video.adapter.jpa.repository;

import it.proconsole.library.video.adapter.ApplicationConfig;
import it.proconsole.library.video.adapter.jpa.model.Film;
import it.proconsole.library.video.adapter.jpa.model.FilmReviewEntity;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmReviewRepository;
import it.proconsole.library.video.core.Fixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ApplicationConfig.class)
@ActiveProfiles("test")
@Transactional
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