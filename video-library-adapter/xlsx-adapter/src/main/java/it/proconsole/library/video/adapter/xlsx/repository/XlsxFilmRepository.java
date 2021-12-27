package it.proconsole.library.video.adapter.xlsx.repository;

import it.proconsole.library.video.adapter.xlsx.repository.adapter.FilmAdapter;
import it.proconsole.library.video.adapter.xlsx.repository.workbook.FilmWorkbookRepository;
import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.core.repository.Protocol;
import org.apache.commons.lang3.NotImplementedException;

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
    return filmAdapter.toDomain(repository.findAll());
  }

  @Override
  public List<Film> saveAll(List<Film> films) {
    throw new NotImplementedException();
  }
}
