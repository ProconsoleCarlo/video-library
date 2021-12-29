package it.proconsole.library.video.adapter.jpa.repository;

import it.proconsole.library.video.adapter.jpa.repository.adapter.FilmAdapter;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmCrudRepository;
import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.core.repository.Protocol;

import java.util.List;

public class JpaFilmRepository implements FilmRepository {
  private final FilmCrudRepository filmCrudRepository;
  private final FilmAdapter filmAdapter;

  public JpaFilmRepository(FilmCrudRepository filmCrudRepository, FilmAdapter filmAdapter) {
    this.filmCrudRepository = filmCrudRepository;
    this.filmAdapter = filmAdapter;
  }

  @Override
  public Protocol protocol() {
    return Protocol.JPA;
  }

  @Override
  public List<Film> findAll() {
    return filmAdapter.toDomain(filmCrudRepository.findAll());
  }

  @Override
  public List<Film> saveAll(List<Film> films) {
    var entities = filmAdapter.fromDomain(films);
    var saved = filmCrudRepository.saveAll(entities);
    return filmAdapter.toDomain(saved);
  }
}
