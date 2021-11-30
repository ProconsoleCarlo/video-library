package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmReviewEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class FilmReviewDao implements DatabaseDao<FilmReviewEntity> {
  private final JdbcTemplate jdbcTemplateObject;

  public FilmReviewDao(DataSource dataSource) {
    this.jdbcTemplateObject = new JdbcTemplate(dataSource);
  }

  @Override
  public List<FilmReviewEntity> findAll() {
    return jdbcTemplateObject.query("select * from film_review", rowMapper());
  }

  public List<FilmReviewEntity> findById(long filmId) {
    return jdbcTemplateObject.query("select * from film_review where film_id = ?", rowMapper(), filmId);
  }

  private RowMapper<FilmReviewEntity> rowMapper() {
    return (rs, rowNum) -> new FilmReviewEntity(
            rs.getLong("id"),
            rs.getTimestamp("date").toLocalDateTime(),
            rs.getInt("rating"),
            rs.getString("detail")
    );
  }
}
