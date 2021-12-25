package it.proconsole.library.video.adapter.xlsx.repository;

import it.proconsole.library.video.adapter.xlsx.repository.adapter.FilmAdapter;
import it.proconsole.library.video.adapter.xlsx.repository.workbook.FilmWorkbookRepository;
import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.repository.FilmRepository;

import java.util.Collections;
import java.util.List;

public class XlsxFilmRepository implements FilmRepository {
  private final FilmWorkbookRepository repository;
  private final FilmAdapter filmAdapter;

  public XlsxFilmRepository(FilmWorkbookRepository repository, FilmAdapter filmAdapter) {
    this.repository = repository;
    this.filmAdapter = filmAdapter;
  }

  @Override
  public List<Film> findAll() {
    return repository.findAll().stream()
            .map(filmAdapter::toDomain)
            .toList();
  }

  @Override
  public List<Film> saveAll(List<Film> films) {
    return Collections.emptyList();
  }
}
