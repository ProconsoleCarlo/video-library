package it.proconsole.library.video.adapter.xlsx.repository;

import it.proconsole.library.video.adapter.xlsx.repository.adapter.FilmAdapter;
import it.proconsole.library.video.adapter.xlsx.repository.workbook.FilmWorkbookRepository;
import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.core.repository.Operation;
import it.proconsole.library.video.core.repository.Protocol;

import java.util.List;

public class XlsxFilmRepository implements FilmRepository {
  private final FilmWorkbookRepository repository;
  private final FilmAdapter filmAdapter;

  public XlsxFilmRepository(FilmWorkbookRepository repository, FilmAdapter filmAdapter) {
    this.repository = repository;
    this.filmAdapter = filmAdapter;
  }

  @Override
  public Protocol protocol() {
    return Protocol.XLSX;
  }

  @Override
  public List<Film> findAll() {
    logStart(protocol(), Operation.FIND_ALL);
    var films = filmAdapter.toDomain(repository.findAll());
    logEnd(protocol(), Operation.FIND_ALL, films.size());
    return films;
  }

  @Override
  public List<Film> saveAll(List<Film> films) {
    logStart(protocol(), Operation.SAVE_ALL, films.size());
    var entities = filmAdapter.fromDomain(films);
    var savedFilms = repository.saveAll(entities);
    logEnd(protocol(), Operation.SAVE_ALL, savedFilms.size());
    return filmAdapter.toDomain(savedFilms);
  }
}
