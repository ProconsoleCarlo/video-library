package it.proconsole.library.video.adapter.xlsx.repository;

import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.repository.FilmRepository;

import java.util.Collections;
import java.util.List;

public class XlsxFilmRepository implements FilmRepository {
  @Override
  public List<Film> findAll() {
    return Collections.emptyList();
  }

  @Override
  public List<Film> saveAll(List<Film> films) {
    return Collections.emptyList();
  }
}
