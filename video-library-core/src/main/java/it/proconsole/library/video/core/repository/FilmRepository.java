package it.proconsole.library.video.core.repository;

import it.proconsole.library.video.core.model.Film;

import java.util.List;

public interface FilmRepository {
  List<Film> findAll();

  List<Film> saveAll(List<Film> films);
}
