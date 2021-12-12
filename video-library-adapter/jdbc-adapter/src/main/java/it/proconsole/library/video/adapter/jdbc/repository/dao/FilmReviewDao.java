package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmReviewEntity;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class FilmReviewDao extends DatabaseDao<FilmReviewEntity> {
  public FilmReviewDao(DataSource dataSource) {
    super(dataSource, "film_review");
  }

  public List<FilmReviewEntity> findByFilmId(Long filmId) {
    return jdbcTemplate().query("select * from film_review where film_id = ?", rowMapper(), filmId);
  }

  @Override
  RowMapper<FilmReviewEntity> rowMapper() {
    return (rs, rowNum) -> new FilmReviewEntity(
        rs.getLong("id"),
        rs.getTimestamp("date").toLocalDateTime(),
        rs.getInt("rating"),
        rs.getString("detail"),
        rs.getInt("film_id")
    );
  }
}
