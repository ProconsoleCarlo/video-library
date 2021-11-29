package it.proconsole.videodb.db.repository;

import it.proconsole.videodb.db.model.FilmTable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class FilmDao implements DatabaseDao<FilmTable> {
  private final JdbcTemplate jdbcTemplateObject;

  public FilmDao(DataSource dataSource) {
    this.jdbcTemplateObject = new JdbcTemplate(dataSource);
  }

  @Override
  public List<FilmTable> findAll() {
    return jdbcTemplateObject.query("select * from film", rowMapper());
  }

  private RowMapper<FilmTable> rowMapper() {
    return (rs, rowNum) -> new FilmTable(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getInt("year")
    );
  }
}
