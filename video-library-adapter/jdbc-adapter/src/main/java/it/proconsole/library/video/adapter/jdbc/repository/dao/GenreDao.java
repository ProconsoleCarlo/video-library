package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.jdbc.model.GenreEntity;
import it.proconsole.library.video.core.model.GenreEnum;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

public class GenreDao extends DatabaseDao<GenreEntity> {
  public GenreDao(DataSource dataSource) {
    super(dataSource, "genre");
  }

  public List<GenreEntity> findByFilmId(Long filmId) {
    return jdbcTemplate().query("select id, value from film_genres join genre g on g.id = film_genres.genre_id where film_id = ?", rowMapper(), filmId);
  }

  public void addToFilmId(List<GenreEntity> entities, Long filmId) {
    var valuesSql = String.join(",", Collections.nCopies(entities.size(), "(" + filmId + ", ?)"));
    var entitiesId = entities.stream().map(GenreEntity::id).toArray();
    jdbcTemplate().update("insert into film_genres values " + valuesSql, entitiesId);
  }

  public void addToFilmId(GenreEntity entity, Long filmId) {
    jdbcTemplate().update("insert into film_genres values (?, ?)", filmId, entity.id());
  }

  public void removeFromFilmId(List<GenreEntity> entities, Long filmId) {
    entities.forEach(it -> removeFromFilmId(it, filmId));
  }

  public void removeFromFilmId(GenreEntity entity, Long filmId) {
    jdbcTemplate().update("delete from film_genres where film_id = ? and genre_id = ?", filmId, entity.id());
  }

  public List<GenreEntity> saveForFilmId(List<GenreEntity> entities, Long filmId) {
    var existentGenres = findByFilmId(filmId);
    var genresToAdd = entities.stream().filter(it -> !existentGenres.contains(it)).toList();
    addToFilmId(genresToAdd, filmId);
    var genresToRemove = existentGenres.stream().filter(it -> !entities.contains(it)).toList();
    removeFromFilmId(genresToRemove, filmId);
    return findByFilmId(filmId);
  }

  @Override
  RowMapper<GenreEntity> rowMapper() {
    return (rs, rowNum) -> new GenreEntity(rs.getLong("id"), GenreEnum.valueOf(rs.getString("value")));
  }
}
