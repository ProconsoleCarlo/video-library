package it.proconsole.videodb.db.repository;

import it.proconsole.videodb.core.model.Genre;
import it.proconsole.videodb.db.model.FilmGenreTable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class FilmGenreDao implements DatabaseDao<FilmGenreTable> {
  private final JdbcTemplate jdbcTemplateObject;

  public FilmGenreDao(DataSource dataSource) {
    this.jdbcTemplateObject = new JdbcTemplate(dataSource);
  }

  @Override
  public List<FilmGenreTable> findAll() {
    return jdbcTemplateObject.query("select * from film_genre", rowMapper());
  }

  public List<FilmGenreTable> findBy(long id) {
    return jdbcTemplateObject.query("select * from film_genre where ", rowMapper());
  }

  private RowMapper<FilmGenreTable> rowMapper() {
    return (rs, rowNum) -> new FilmGenreTable(
            rs.getLong("filmId"),
            Genre.valueOf(rs.getString("genre"))
    );
  }
}
