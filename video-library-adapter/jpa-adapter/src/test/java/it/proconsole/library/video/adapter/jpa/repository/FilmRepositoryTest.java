package it.proconsole.library.video.adapter.jpa.repository;

import it.proconsole.library.video.adapter.ApplicationConfig;
import it.proconsole.library.video.adapter.jpa.model.Film;
import it.proconsole.library.video.core.Fixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ApplicationConfig.class)
@ActiveProfiles("test")
@Transactional
class FilmRepositoryTest {
  private static final String FILMS_JSON = "/it/proconsole/library/video/core/model/films.json";

  @Autowired
  private FilmRepository filmRepository;

  @Test
  void findAll() {
    var all = filmRepository.findAll();

    assertEquals(Fixtures.readListFromClasspath(FILMS_JSON, Film.class), all);
  }
}
