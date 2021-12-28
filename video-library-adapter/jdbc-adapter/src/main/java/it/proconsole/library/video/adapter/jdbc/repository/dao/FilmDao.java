package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.jdbc.entity.FilmEntity;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

public class FilmDao extends DatabaseDao<FilmEntity> {
  public FilmDao(DataSource dataSource) {
    super(dataSource, "film");
  }

  @Override
  RowMapper<FilmEntity> rowMapper() {
    return (rs, rowNum) -> new FilmEntity(
        rs.getLong("id"),
        rs.getString("title"),
        rs.getInt("year")
    );
  }
}
