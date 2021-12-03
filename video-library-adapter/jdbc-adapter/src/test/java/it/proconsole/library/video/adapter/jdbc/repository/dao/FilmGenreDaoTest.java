package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.ApplicationConfig;
import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmGenreEntity;
import it.proconsole.library.video.core.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ApplicationConfig.class)
@Profile("test")
class FilmGenreDaoTest {
  private static final String FILM_GENRES_JSON = "/it/proconsole/library/video/adapter/model/filmGenres.json";

  @Autowired
  private DataSource dataSource;

  private FilmGenreDao dao;

  @BeforeEach
  void setUp() {
    dao = new FilmGenreDao(dataSource);
  }

  @Test
  void findAll() {
    var current = dao.findAll();
    var expected = Fixtures.readListFromClasspath(FILM_GENRES_JSON, FilmGenreEntity.class);

    assertEquals(expected, current);
  }

  @Nested
  class WhenFindByFilmId {
    @Test
    void listIfPresent() {
      var current = dao.findByFilmId(1);
      var expected = List.of(
              new FilmGenreEntity(1, 3),
              new FilmGenreEntity(1, 4)
      );

      assertEquals(expected, current);
    }

    @Test
    void emptyListIfAbsent() {
      var current = dao.findByFilmId(10);

      assertTrue(current.isEmpty());
    }
  }
}