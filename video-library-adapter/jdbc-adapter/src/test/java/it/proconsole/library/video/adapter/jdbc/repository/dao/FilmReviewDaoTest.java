package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.ApplicationConfig;
import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmReviewEntity;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.Month;

@SpringBootTest(classes = ApplicationConfig.class)
@Profile("test")
class FilmReviewDaoTest extends DatabaseDaoTest<FilmReviewEntity> {
  @Autowired
  private DataSource dataSource;

  @BeforeEach
  void setUp() {
    dao = new FilmReviewDao(dataSource);
  }

  @Override
  FilmReviewEntity anEntity() {
    return new FilmReviewEntity(LocalDateTime.of(2012, Month.DECEMBER, 31, 0, 0), 7, "This is a review", 1);
  }
}