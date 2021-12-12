package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.jdbc.repository.entity.GenreEntity;
import it.proconsole.library.video.core.model.GenreEnum;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

public class GenreDao extends DatabaseDao<GenreEntity> {
  public GenreDao(DataSource dataSource) {
    super(dataSource, "genre");
  }

  @Override
  RowMapper<GenreEntity> rowMapper() {
    return (rs, rowNum) -> new GenreEntity(rs.getLong("id"), GenreEnum.valueOf(rs.getString("value")));
  }
}
