package it.proconsole.library.video.adapter.xlsx.repository.workbook;

import it.proconsole.library.video.adapter.xlsx.exception.InvalidXlsxFileException;
import it.proconsole.library.video.adapter.xlsx.model.FilmRow;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;

public class FilmWorkbookRepository {
  private static final int FILM_SHEET = 0;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final String xlsxPath;

  public FilmWorkbookRepository(String xlsxPath) {
    this.xlsxPath = xlsxPath;
    //"./src/main/resources/CatalogoFilm.xlsx"
  }

  public List<FilmRow> findAll() {
    try {
      var sheets = new XSSFWorkbook(xlsxPath);
      var filmsSheet = sheets.getSheetAt(FILM_SHEET);
      var spliterator = filmsSheet.spliterator();
      return StreamSupport.stream(spliterator, false)
              .skip(3)
              .filter(this::emptyRows)
              .map(this::adaptRow)
              .toList();
    } catch (IOException | InvalidOperationException e) {
      logger.error("An error as occurred trying to read {}", xlsxPath, e);
      throw new InvalidXlsxFileException();
    }
  }

  private boolean emptyRows(Row row) {
    return row.getCell(0) != null;
  }

  private FilmRow adaptRow(Row row) {
    return new FilmRow(
            (long) row.getRowNum(),
            row.getCell(0).getStringCellValue(),
            (int) row.getCell(1).getNumericCellValue(),
            row.getCell(3).getStringCellValue()
    );
  }
}
