package it.proconsole.library.video.adapter.xlsx.repository.workbook;

import it.proconsole.library.video.adapter.xlsx.exception.InvalidXlsxFileException;
import it.proconsole.library.video.adapter.xlsx.model.FilmRow;
import it.proconsole.library.video.core.Fixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmWorkbookRepositoryTest {
  private final FilmWorkbookRepository repository = new FilmWorkbookRepository("./src/test/resources/TestCatalogoFilm.xlsx");

  @Test
  void findAll() {
    var current = repository.findAll();

    assertEquals(Fixtures.readListFromClasspath("/filmRows.json", FilmRow.class), current);
  }

  @ParameterizedTest
  @ValueSource(strings = {"/invalidPath/TestCatalogoFilm.xlsx", "./src/test/resources/InvalidFile.xlsx"})
  void invalidXlsxFile(String path) {
    assertThrows(InvalidXlsxFileException.class, () -> new FilmWorkbookRepository(path).findAll());
  }
}