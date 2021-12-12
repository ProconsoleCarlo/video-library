package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.ApplicationConfig;
import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmEntity;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@SpringBootTest(classes = ApplicationConfig.class)
@Profile("test")
class FilmDaoTest extends DatabaseDaoTest<FilmEntity> {
  @Autowired
  private DataSource dataSource;

  @BeforeEach
  void setUp() {
    dao = new FilmDao(dataSource);

    new FilmReviewDao(dataSource).findAll();
    //dao.deleteById(1L);
  }

  @Override
  FilmEntity anEntity() {
    return new FilmEntity("Film title", 2020);
  }
}