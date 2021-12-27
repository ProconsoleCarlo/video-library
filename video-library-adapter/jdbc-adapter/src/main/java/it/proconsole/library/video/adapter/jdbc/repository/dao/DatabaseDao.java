package it.proconsole.library.video.adapter.jdbc.repository.dao;

import it.proconsole.library.video.adapter.jdbc.repository.entity.EntityWithId;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class DatabaseDao<T extends EntityWithId> {
  private final JdbcTemplate jdbcTemplate;
  private final SimpleJdbcInsert simpleJdbcInsert;
  private final String tableName;

  public DatabaseDao(DataSource dataSource, String tableName) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(tableName).usingGeneratedKeyColumns("id");
    this.tableName = tableName;
  }

  public JdbcTemplate jdbcTemplate() {
    return jdbcTemplate;
  }

  public void delete(T entity) {
    deleteById(entity.id());
  }

  public void deleteAll() {
    jdbcTemplate().update("delete from " + tableName);
  }

  public void deleteAll(List<T> entities) {
    entities.forEach(this::delete);
  }

  public void deleteById(Long id) {
    jdbcTemplate.update("delete from " + tableName + " where id = ?", id);
  }

  public void deleteAllById(List<Long> ids) {
    ids.forEach(this::deleteById);
  }

  public List<T> findAll() {
    return jdbcTemplate().query("select * from " + tableName, rowMapper());
  }

  public List<T> findAllById(List<Long> ids) {
    var inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
    return jdbcTemplate().query("select * from " + tableName + " where id in (" + inSql + ")", rowMapper(), ids.toArray());
  }

  public Optional<T> findById(Long id) {
    return jdbcTemplate().query("select * from " + tableName + " where id = ?", rowMapper(), id).stream().findFirst();
  }

  public T save(T entity) {
    return findById(entity.id())
            .map(e -> update(entity))
            .orElseGet(() -> insert(entity));
  }

  public List<T> saveAll(List<T> entity) {
    return entity.stream().map(this::save).toList();
  }

  abstract RowMapper<T> rowMapper();

  private T insert(T entity) {
    var id = simpleJdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(entity));
    return findById(id.longValue()).orElseThrow();
  }

  private T update(T entity) {
    var params = entity.data().values().toArray();
    var setSql = entity.data().keySet().stream().map(c -> c + "=?").collect(Collectors.joining(","));
    jdbcTemplate.update("update " + tableName + " set " + setSql + " where id = " + entity.id(), params);
    return findById(entity.id()).orElseThrow();
  }
  /*

  public boolean existsById(ID id) {
    return false;
  }
*/
}
