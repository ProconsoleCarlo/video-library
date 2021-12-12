package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.ApplicationConfig;
import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmGenreEntity;
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
  @Autowired
  private DataSource dataSource;

  private FilmGenreDao dao;

  @BeforeEach
  void setUp() {
    dao = new FilmGenreDao(dataSource);
  }

  FilmGenreEntity anEntity() {
    return new FilmGenreEntity(1L, 1L);
  }

  @Nested
  class WhenFindByFilmId {
    @Test
    void listIfPresent() {
      var entities = List.of(
          new FilmGenreEntity(1L, 3L),
          new FilmGenreEntity(1L, 4L)
      );
      dao.saveAll(entities);

      var current = dao.findByFilmId(1);

      assertEquals(entities, current);
    }

    @Test
    void emptyListIfAbsent() {
      var current = dao.findByFilmId(10);

      assertTrue(current.isEmpty());
    }
  }
}