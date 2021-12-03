package it.proconsole.library.video.adapter.jdbc.repository;

import it.proconsole.library.video.adapter.ApplicationConfig;
import it.proconsole.library.video.adapter.jdbc.model.Film;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmGenreDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmReviewDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.GenreDao;
import it.proconsole.library.video.core.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ApplicationConfig.class)
@Profile("test")
class FilmRepositoryTest {
  private static final String FILMS_JSON = "/it/proconsole/library/video/core/model/films.json";

  @Autowired
  private FilmDao filmDao;
  @Autowired
  private FilmGenreDao filmGenreDao;
  @Autowired
  private GenreDao genreDao;
  @Autowired
  private FilmReviewDao filmReviewDao;

  private FilmRepository repository;

  @BeforeEach
  void setUp() {
    repository = new FilmRepository(filmDao, filmGenreDao, genreDao, filmReviewDao);
  }

  @Test
  void findAll() {
    var current = repository.findAll();
    var expected = Fixtures.readListFromClasspath(FILMS_JSON, Film.class);

    assertEquals(expected, current);
  }
}