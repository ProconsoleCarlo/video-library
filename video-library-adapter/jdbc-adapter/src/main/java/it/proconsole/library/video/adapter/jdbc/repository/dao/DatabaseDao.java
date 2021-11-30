package it.proconsole.library.video.adapter.jdbc.repository.dao;

import java.util.List;

public interface DatabaseDao<T> {
  List<T> findAll();

  /*public <S extends T> S save(S entity) {
    return null;
  }

  public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
    return null;
  }

  public Optional<T> findById(ID id) {
    return Optional.empty();
  }

  public boolean existsById(ID id) {
    return false;
  }

  public Iterable<T> findAll() {
    return null;
  }

  public Iterable<T> findAllById(Iterable<ID> ids) {
    return null;
  }

  public long count() {
    return 0;
  }

  public void deleteById(ID id) {

  }

  public void delete(T entity) {

  }

  public void deleteAllById(Iterable<? extends ID> ids) {

  }

  public void deleteAll(Iterable<? extends T> entities) {

  }

  public void deleteAll() {

  }*/
}
