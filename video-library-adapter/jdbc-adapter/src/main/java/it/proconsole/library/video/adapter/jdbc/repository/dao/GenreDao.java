package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.jdbc.model.GenreEnum;
import it.proconsole.library.video.adapter.jdbc.repository.entity.GenreEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class GenreDao implements DatabaseDao<GenreEntity> {
  private final JdbcTemplate jdbcTemplateObject;

  public GenreDao(DataSource dataSource) {
    this.jdbcTemplateObject = new JdbcTemplate(dataSource);
  }

  @Override
  public List<GenreEntity> findAll() {
    return jdbcTemplateObject.query("select * from genre", rowMapper());
  }

  public Optional<GenreEntity> findById(long id) {
    return jdbcTemplateObject.query("select * from genre where id = ?", rowMapper(), id).stream().findFirst();
  }

  private RowMapper<GenreEntity> rowMapper() {
    return (rs, rowNum) -> new GenreEntity(rs.getInt("id"), GenreEnum.valueOf(rs.getString("value")));
  }
}
