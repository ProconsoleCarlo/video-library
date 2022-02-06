package it.proconsole.library.video.adapter.jpa.repository;

import it.proconsole.library.video.adapter.jpa.repository.adapter.FilmAdapter;
import it.proconsole.library.video.adapter.jpa.repository.crud.FilmCrudRepository;
import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.core.repository.Operation;
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
    logStart(protocol(), Operation.FIND_ALL);
    var films = filmAdapter.toDomain(filmCrudRepository.findAll());
    logEnd(protocol(), Operation.FIND_ALL, films.size());
    return films;
  }

  @Override
  public List<Film> saveAll(List<Film> films) {
    logStart(protocol(), Operation.SAVE_ALL, films.size());
    var entities = filmAdapter.fromDomain(films);
    var savedFilms = filmCrudRepository.saveAll(entities);
    logEnd(protocol(), Operation.SAVE_ALL, savedFilms.size());
    return filmAdapter.toDomain(savedFilms);
  }
}
