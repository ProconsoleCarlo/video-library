package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.ApplicationConfig;
import it.proconsole.library.video.adapter.jdbc.repository.entity.GenreEntity;
import it.proconsole.library.video.core.Fixtures;
import it.proconsole.library.video.core.model.GenreEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ApplicationConfig.class)
@Profile("test")
class GenreDaoTest {
  private static final String GENRES_JSON = "/it/proconsole/library/video/adapter/model/genreEntities.json";

  @Autowired
  private DataSource dataSource;

  private GenreDao dao;

  @BeforeEach
  void setUp() {
    dao = new GenreDao(dataSource);
  }

  @Test
  void findAll() {
    var current = dao.findAll();
    var expected = Fixtures.readListFromClasspath(GENRES_JSON, GenreEntity.class);

    assertEquals(expected, current);
  }

  @Nested
  class WhenFindById {
    @Test
    void idIsPresent() {
      var current = dao.findById(1);

      assertTrue(current.isPresent());
      assertEquals(new GenreEntity(1, GenreEnum.ACTION), current.get());
    }

    @Test
    void idIsAbsent() {
      var current = dao.findById(10);

      assertFalse(current.isPresent());
    }
  }
}