package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.jdbc.repository.entity.GenreEntity;
import it.proconsole.library.video.core.model.GenreEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JdbcTest
@Sql({"/schema.sql"})
class GenreDaoTest extends DatabaseDaoTest<GenreEntity> {
  @Autowired
  private DataSource dataSource;

  @BeforeEach
  void setUp() {
    dao = new GenreDao(dataSource);
  }

  @Override
  GenreEntity anEntity() {
    return new GenreEntity(null, GenreEnum.ACTION);
  }

  @Override
  GenreEntity anEntityForUpdate(Long id) {
    return new GenreEntity(id, GenreEnum.COMEDY);
  }

  @Test
  void findByFilmId() {
    var entities = dao.saveAll(List.of(
            new GenreEntity(GenreEnum.ACTION.id(), GenreEnum.ACTION),
            new GenreEntity(GenreEnum.ADVENTURE.id(), GenreEnum.ADVENTURE)
    ));
    dao.jdbcTemplate().update(
            """
                    insert into `film`
                    values (1, 'Film title', 2011);
                    insert into `film_genres`
                    values (1, %d),
                           (1, %d);
                           """.formatted(GenreEnum.ACTION.id(), GenreEnum.ADVENTURE.id())
    );


    var current = ((GenreDao) dao).findByFilmId(1L);

    assertEquals(entities, current);

    cleanSupportTables();
  }

  @Test
  void saveByFilmId() {
    var entities = dao.saveAll(List.of(
            new GenreEntity(GenreEnum.ACTION),
            new GenreEntity(GenreEnum.ADVENTURE)
    ));

    dao.jdbcTemplate().update(
            """
                    insert into film
                    values (1, 'Film title', 2011);
                    insert into film_genres
                    values (1, %d)
                    """.formatted(GenreEnum.ACTION.id())
    );

    ((GenreDao) dao).addToFilmId(new GenreEntity(GenreEnum.ADVENTURE), 1L);

    var current = ((GenreDao) dao).findByFilmId(1L);

    assertEquals(entities, current);

    cleanSupportTables();
  }

  private void cleanSupportTables() {
    dao.jdbcTemplate().update("delete from film_genres; delete from film;");
  }
}