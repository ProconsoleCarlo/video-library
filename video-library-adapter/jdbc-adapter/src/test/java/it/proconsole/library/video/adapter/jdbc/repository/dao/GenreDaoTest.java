package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.jdbc.repository.entity.GenreEntity;
import it.proconsole.library.video.core.model.GenreEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Sql({"/schema.sql"})
class GenreDaoTest extends DatabaseDaoTest<GenreEntity> {
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private DataSource dataSource;

  @BeforeEach
  void setUp() {
    jdbcTemplate = new JdbcTemplate(dataSource);
    dao = new GenreDao(dataSource);
  }

  @Override
  GenreEntity anEntity() {
    return new GenreEntity(GenreEnum.ACTION);
  }

  @Override
  GenreEntity anEntityForUpdate(Long id) {
    return new GenreEntity(id, GenreEnum.ACTION);
  }

  @Test
  void findByFilmId() {
    var entities = dao.saveAll(List.of(
            new GenreEntity(GenreEnum.COMEDY),
            new GenreEntity(GenreEnum.ACTION)
    ));
    var ids = entities.stream().map(GenreEntity::id).toList();
    jdbcTemplate.update(
            "insert into `film`"
                    + "VALUES (1, 'Film title', 2011);"
                    + "insert into `film_genres`"
                    + "VALUES (1, " + ids.get(0) + "),"
                    + "       (1, " + ids.get(1) + ");"
    );


    var current = ((GenreDao) dao).findByFilmId(1L);

    assertEquals(entities, current);

    jdbcTemplate.update(
            "delete from film_genres; delete from film;"
    );
  }
}