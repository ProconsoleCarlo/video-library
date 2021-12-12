package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmGenreEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class FilmGenreDao {
  private final JdbcTemplate jdbcTemplate;

  public FilmGenreDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public List<FilmGenreEntity> findByFilmId(long filmId) {
    return jdbcTemplate.query("select * from film_genres where film_id = ?", rowMapper(), filmId);
  }

  RowMapper<FilmGenreEntity> rowMapper() {
    return (rs, rowNum) -> new FilmGenreEntity(
        rs.getLong("film_id"),
        rs.getLong("genre_id")
    );
  }
}
