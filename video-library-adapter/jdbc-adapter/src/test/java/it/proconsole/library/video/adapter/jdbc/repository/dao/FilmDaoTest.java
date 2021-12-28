package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.jdbc.model.FilmEntity;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;

@JdbcTest
@Sql({"/schema.sql"})
class FilmDaoTest extends DatabaseDaoTest<FilmEntity> {
  @Autowired
  private DataSource dataSource;

  @BeforeEach
  void setUp() {
    dao = new FilmDao(dataSource);

    new FilmReviewDao(dataSource).findAll();
  }

  @Override
  FilmEntity anEntity() {
    return new FilmEntity("Film title", 2020);
  }

  @Override
  FilmEntity anEntityForUpdate(Long id) {
    return new FilmEntity(id, "Film title", 2020);
  }
}