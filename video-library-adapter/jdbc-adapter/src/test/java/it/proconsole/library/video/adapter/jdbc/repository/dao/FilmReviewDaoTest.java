package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.ApplicationConfig;
import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmReviewEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.Month;

@SpringBootTest(classes = ApplicationConfig.class)
@Profile("test")
class FilmReviewDaoTest extends DatabaseDaoTest<FilmReviewEntity> {
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private DataSource dataSource;

  @BeforeEach
  void setUp() {
    jdbcTemplate = new JdbcTemplate(dataSource);
    dao = new FilmReviewDao(dataSource);

    jdbcTemplate.update(
        "insert into `film`" +
            "VALUES (1, 'Film title', 2011);"
    );
  }

  @Override
  @AfterEach
  void tearDown() {
    super.tearDown();
    jdbcTemplate.update(
        "delete from film where id = 1"
    );
  }

  @Override
  FilmReviewEntity anEntity() {
    return new FilmReviewEntity(LocalDateTime.of(2012, Month.DECEMBER, 31, 0, 0), 7, "This is a review", 1L);
  }
}