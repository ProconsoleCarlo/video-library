package it.proconsole.library.video.adapter.jpa.repository.crud;

import it.proconsole.library.video.adapter.jpa.model.CompleteFilmEntity;
import it.proconsole.library.video.core.Fixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql({"/schema.sql", "/data.sql"})
class FilmCrudRepositoryTest {
  private static final String FILMS_JSON = "/it/proconsole/library/video/core/model/films.json";

  @Autowired
  private FilmCrudRepository filmRepository;

  @Test
  void findAll() {
    var all = filmRepository.findAll();

    assertEquals(Fixtures.readListFromClasspath(FILMS_JSON, CompleteFilmEntity.class), all);
  }
}
