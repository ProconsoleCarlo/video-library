package it.proconsole.library.video.adapter.jdbc.repository;

import it.proconsole.library.video.core.model.Genre;
import it.proconsole.library.video.adapter.jdbc.model.GenreTable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class GenreDao implements DatabaseDao<GenreTable> {
  private final JdbcTemplate jdbcTemplateObject;

  public GenreDao(DataSource dataSource) {
    this.jdbcTemplateObject = new JdbcTemplate(dataSource);
  }

  @Override
  public List<GenreTable> findAll() {
    return jdbcTemplateObject.query("select * from genre", rowMapper());
  }

  private RowMapper<GenreTable> rowMapper() {
    return (rs, rowNum) -> new GenreTable(Genre.valueOf(rs.getString("value")));
  }
}
