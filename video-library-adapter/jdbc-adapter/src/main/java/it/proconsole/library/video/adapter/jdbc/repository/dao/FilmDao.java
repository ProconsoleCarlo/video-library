package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.jdbc.repository.entity.FilmEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class FilmDao implements DatabaseDao<FilmEntity> {
  private final JdbcTemplate jdbcTemplateObject;

  public FilmDao(DataSource dataSource) {
    this.jdbcTemplateObject = new JdbcTemplate(dataSource);
  }

  @Override
  public List<FilmEntity> findAll() {
    return jdbcTemplateObject.query("select * from film", rowMapper());
  }

  private RowMapper<FilmEntity> rowMapper() {
    return (rs, rowNum) -> new FilmEntity(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getInt("year")
    );
  }
}
