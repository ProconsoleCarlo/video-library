package it.proconsole.library.video.adapter.jpa.repository;

import it.proconsole.library.video.adapter.ApplicationConfig;
import it.proconsole.library.video.adapter.Fixtures;
import it.proconsole.library.video.adapter.jpa.model.Film;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApplicationConfig.class)
@ActiveProfiles("test")
@Transactional
class FilmRepositoryTest {
  private static final String FILMS_JSON = "/films.json";

  @Autowired
  private FilmRepository filmRepository;

  @Test
  void name() {
    var all = filmRepository.findAll();

    assertEquals(Fixtures.readListFromClasspath(FILMS_JSON, Film.class), all);
  }
}
