package it.proconsole.library.video.adapter.xlsx.repository;

import it.proconsole.library.video.adapter.xlsx.model.FilmRow;
import it.proconsole.library.video.adapter.xlsx.repository.adapter.FilmAdapter;
import it.proconsole.library.video.adapter.xlsx.repository.workbook.FilmWorkbookRepository;
import it.proconsole.library.video.core.model.Film;
import it.proconsole.library.video.core.repository.FilmRepository;
import it.proconsole.library.video.core.repository.Protocol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class XlsxFilmRepositoryTest {
  private static final List<Film> DOMAIN = List.of(
          new Film(1L, "Title", 2021, Collections.emptyList(), Collections.emptyList()),
          new Film(2L, "Another title", 2012, Collections.emptyList(), Collections.emptyList())
  );
  private static final List<FilmRow> FRONTIER = List.of(
          new FilmRow(1L, "Title", 2021, Collections.emptyList(), Collections.emptyList()),
          new FilmRow(2L, "Another title", 2012, Collections.emptyList(), Collections.emptyList())
  );

  @Mock
  private FilmWorkbookRepository filmWorkbookRepository;
  @Mock
  private FilmAdapter filmAdapter;

  private FilmRepository repository;

  @BeforeEach
  void setUp() {
    repository = new XlsxFilmRepository(filmWorkbookRepository, filmAdapter);
  }

  @Test
  void protocol() {
    assertEquals(Protocol.XLSX, repository.protocol());
  }

  @Test
  void findAll() {
    when(filmWorkbookRepository.findAll()).thenReturn(FRONTIER);
    when(filmAdapter.toDomain(FRONTIER)).thenReturn(DOMAIN);

    var current = repository.findAll();

    assertEquals(DOMAIN, current);
  }

  @Test
  void saveAll() {
    when(filmAdapter.fromDomain(DOMAIN)).thenReturn(FRONTIER);
    when(filmWorkbookRepository.saveAll(FRONTIER)).thenReturn(FRONTIER);
    when(filmAdapter.toDomain(FRONTIER)).thenReturn(DOMAIN);

    var current = repository.saveAll(DOMAIN);

    assertEquals(DOMAIN, current);
  }
}