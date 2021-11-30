package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmGenreEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class FilmGenreDao implements DatabaseDao<FilmGenreEntity> {
  private final JdbcTemplate jdbcTemplateObject;

  public FilmGenreDao(DataSource dataSource) {
    this.jdbcTemplateObject = new JdbcTemplate(dataSource);
  }

  @Override
  public List<FilmGenreEntity> findAll() {
    return jdbcTemplateObject.query("select * from film_genres", rowMapper());
  }

  public List<FilmGenreEntity> findBy(long filmId) {
    return jdbcTemplateObject.query("select * from film_genres where film_id = ?", rowMapper(), filmId);
  }

  private RowMapper<FilmGenreEntity> rowMapper() {
    return (rs, rowNum) -> new FilmGenreEntity(
            rs.getLong("film_id"),
            rs.getInt("genre_id")
    );
  }
}
