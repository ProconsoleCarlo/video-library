package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.ApplicationConfig;
import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmEntity;
import it.proconsole.library.video.core.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ApplicationConfig.class)
@Profile("test")
class FilmDaoTest {
  private static final String FILMS_JSON = "/it/proconsole/library/video/adapter/model/films.json";

  @Autowired
  private DataSource dataSource;

  private FilmDao dao;

  @BeforeEach
  void setUp() {
    dao = new FilmDao(dataSource);
  }

  @Test
  void findAll() {
    var current = dao.findAll();
    var expected = Fixtures.readListFromClasspath(FILMS_JSON, FilmEntity.class);

    assertEquals(expected, current);
  }
}