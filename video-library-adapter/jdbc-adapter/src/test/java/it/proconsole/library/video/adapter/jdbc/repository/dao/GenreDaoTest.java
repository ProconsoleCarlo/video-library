package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.ApplicationConfig;
import it.proconsole.library.video.adapter.jdbc.repository.entity.GenreEntity;
import it.proconsole.library.video.core.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ApplicationConfig.class)
@Profile("test")
class GenreDaoTest {
  private static final String FILMS_JSON = "/it/proconsole/library/video/adapter/model/genres.json";

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private GenreDao genreDao;

  @BeforeEach
  void setUp() {
    genreDao = new GenreDao(jdbcTemplate.getDataSource());
  }

  @Test
  void findAll() {
    var genres = genreDao.findAll();

    assertEquals(Fixtures.readListFromClasspath(FILMS_JSON, GenreEntity.class), genres);
  }
}