package it.proconsole.library.video.adapter.jdbc.repository;

import it.proconsole.library.video.adapter.jdbc.model.Film;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.FilmReviewDao;
import it.proconsole.library.video.adapter.jdbc.repository.dao.GenreDao;
import it.proconsole.library.video.core.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Sql({"/schema.sql", "/data.sql"})
class FilmRepositoryTest {
  private static final String FILMS_JSON = "/it/proconsole/library/video/core/model/films.json";

  @Autowired
  private DataSource dataSource;

  private FilmRepository repository;

  @BeforeEach
  void setUp() {
    repository = new FilmRepository(new FilmDao(dataSource), new GenreDao(dataSource), new FilmReviewDao(dataSource));
  }

  @Test
  void findAll() {
    var current = repository.findAll();
    var expected = Fixtures.readListFromClasspath(FILMS_JSON, Film.class);

    assertEquals(expected, current);
  }
}