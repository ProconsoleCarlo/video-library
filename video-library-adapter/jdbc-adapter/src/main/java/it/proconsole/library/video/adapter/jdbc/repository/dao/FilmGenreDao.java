package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmGenreEntity;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class FilmGenreDao extends DatabaseDao<FilmGenreEntity> {
  public FilmGenreDao(DataSource dataSource) {
    super(dataSource, "film_genres");
  }

  public List<FilmGenreEntity> findByFilmId(long filmId) {
    return jdbcTemplate().query("select * from film_genres where film_id = ?", rowMapper(), filmId);
  }

  @Override
  RowMapper<FilmGenreEntity> rowMapper() {
    return (rs, rowNum) -> new FilmGenreEntity(
        rs.getLong("film_id"),
        rs.getInt("genre_id")
    );
  }
}
