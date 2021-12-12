package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.ApplicationConfig;
import it.proconsole.library.video.adapter.jdbc.repository.entity.GenreEntity;
import it.proconsole.library.video.core.model.GenreEnum;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@SpringBootTest(classes = ApplicationConfig.class)
@Profile("test")
class GenreDaoTest extends DatabaseDaoTest<GenreEntity> {
  @Autowired
  private DataSource dataSource;

  @BeforeEach
  void setUp() {
    dao = new GenreDao(dataSource);
  }

  @Override
  GenreEntity anEntity() {
    return new GenreEntity(1L, GenreEnum.ACTION);
  }
}