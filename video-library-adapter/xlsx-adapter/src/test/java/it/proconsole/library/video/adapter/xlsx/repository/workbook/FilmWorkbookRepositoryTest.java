package it.proconsole.library.video.adapter.xlsx.repository.workbook;

import it.proconsole.library.video.adapter.xlsx.exception.InvalidXlsxFileException;
import it.proconsole.library.video.adapter.xlsx.model.FilmRow;
import it.proconsole.library.video.core.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmWorkbookRepositoryTest {
  private final FilmWorkbookRepository repository = new FilmWorkbookRepository("./src/test/resources/TestCatalogoFilm.xlsx");

  @BeforeEach
  void setUp() {
    repository.deleteAll();
  }

  @Test
  void findAll() {
    repository.saveAll(Fixtures.readListFromClasspath("/filmRows.json", FilmRow.class));

    var current = repository.findAll();

    assertEquals(Fixtures.readListFromClasspath("/filmRows.json", FilmRow.class), current);
  }

  @Test
  void findAllById() {
    var film = new FilmRow(1L, "A title", 2021, Collections.emptyList(), Collections.emptyList());
    var films = List.of(
            film,
            film.copy().withId(2L).build()
    );
    repository.saveAll(films);

    var current = repository.findAllById(List.of(100L));

    assertEquals(List.of(film), current);
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
}