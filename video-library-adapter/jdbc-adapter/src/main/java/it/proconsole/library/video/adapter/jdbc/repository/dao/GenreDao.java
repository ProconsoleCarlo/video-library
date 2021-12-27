package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.jdbc.repository.entity.GenreEntity;
import it.proconsole.library.video.core.model.GenreEnum;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class GenreDao extends DatabaseDao<GenreEntity> {
  public GenreDao(DataSource dataSource) {
    super(dataSource, "genre");
  }

  public List<GenreEntity> findByFilmId(Long filmId) {
    return jdbcTemplate().query("select id, value from film_genres join genre g on g.id = film_genres.genre_id where film_id = ?", rowMapper(), filmId);
  }

  public void addToFilmId(GenreEntity entity, Long filmId) {
    jdbcTemplate().update("insert into film_genres values (?, ?)", filmId, entity.id());
  }

  @Override
  RowMapper<GenreEntity> rowMapper() {
    return (rs, rowNum) -> new GenreEntity(rs.getLong("id"), GenreEnum.valueOf(rs.getString("value")));
  }
}
