package it.proconsole.library.video.adapter.xlsx.repository.workbook;

import it.proconsole.library.video.adapter.xlsx.exception.InvalidXlsxFileException;
import it.proconsole.library.video.adapter.xlsx.model.FilmRow;
import it.proconsole.library.video.core.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static it.proconsole.library.video.adapter.xlsx.model.FilmRow.Builder.aFilmRow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilmWorkbookRepositoryTest {
  private final FilmWorkbookRepository repository = new FilmWorkbookRepository("./src/test/resources/TestCatalogoFilm.xlsx");

  @BeforeEach
  void setUp() {
    repository.deleteAll();
  }

  @Test
  void deleteAllRows() {
    repository.saveAll(List.of(anEntity(), anEntity()));

    repository.deleteAll();

    assertTrue(repository.findAll().isEmpty());
  }

  @Test
  void findAll() {
    repository.saveAll(Fixtures.readListFromClasspath("/filmRows.json", FilmRow.class));

    var current = repository.findAll();

    assertEquals(Fixtures.readListFromClasspath("/filmRows.json", FilmRow.class), current);
  }

  @Nested
  class WhenFindAllById {
    @Test
    void listIfPresent() {
      var films = repository.saveAll(List.of(anEntity(), anEntity()));
      var ids = films.stream().map(FilmRow::id).toList();

      var current = repository.findAllById(ids);

      assertEquals(films, current);
    }

    @Test
    void emptyIfAbsent() {
      var current = repository.findAllById(List.of(Long.MAX_VALUE));

      assertTrue(current.isEmpty());
    }
  }


  @Test
  void saveAll() {
    var current = repository.saveAll(Fixtures.readListFromClasspath("/newFilmRows.json", FilmRow.class));

    assertEquals(Fixtures.readListFromClasspath("/newFilmRows.json", FilmRow.class), current);

    repository.saveAll(Fixtures.readListFromClasspath("/filmRows.json", FilmRow.class));
  }

  @ParameterizedTest
  @ValueSource(strings = {"/invalidPath/TestCatalogoFilm.xlsx", "./src/test/resources/InvalidFile.xlsx"})
  void invalidXlsxFile(String path) {
    assertThrows(InvalidXlsxFileException.class, () -> new FilmWorkbookRepository(path).findAll());
  }

  private FilmRow anEntity() {
    return aFilmRow("Title", 2021).build();
  }
}