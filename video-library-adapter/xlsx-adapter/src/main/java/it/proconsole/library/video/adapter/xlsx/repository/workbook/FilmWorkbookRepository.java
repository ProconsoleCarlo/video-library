package it.proconsole.library.video.adapter.xlsx.repository.workbook;

import it.proconsole.library.video.adapter.xlsx.exception.InvalidXlsxFileException;
import it.proconsole.library.video.adapter.xlsx.model.FilmReviewRow;
import it.proconsole.library.video.adapter.xlsx.model.FilmRow;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static it.proconsole.library.video.adapter.xlsx.repository.workbook.CellUtil.isEmpty;

public class FilmWorkbookRepository {
  private static final LocalDateTime FALLBACK_DATE = LocalDateTime.of(2012, Month.JANUARY, 1, 0, 0);

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final String xlsxPath;

  public FilmWorkbookRepository(String xlsxPath) {
    this.xlsxPath = xlsxPath;
  }

  public void deleteAll() {
    try (var workbook = new XSSFWorkbook(new FileInputStream(xlsxPath))) {
      var filmsSheet = workbook.getSheetAt(Sheet.FILM.id());
      for (int i = Sheet.FILM.rowsToSkip(); i <= filmsSheet.getLastRowNum(); i++) {
        filmsSheet.removeRow(filmsSheet.getRow(i));
      }
      save(workbook);
    } catch (IOException | InvalidOperationException e) {
      logger.error("Error trying to read {}", xlsxPath, e);
      throw new InvalidXlsxFileException(xlsxPath, e);
    }
  }

  public List<FilmRow> findAll() {
    try (var sheets = new XSSFWorkbook(xlsxPath)) {
      return rowsOf(sheets.getSheetAt(Sheet.FILM.id())).map(this::adaptRow).toList();
    } catch (IOException | InvalidOperationException e) {
      logger.error("Error trying to read {}", xlsxPath, e);
      throw new InvalidXlsxFileException(xlsxPath, e);
    }
  }

  public List<FilmRow> findAllById(List<Long> ids) {
    return findAll().stream().filter(it -> ids.contains(it.id())).toList();
  }

  public List<FilmRow> saveAll(List<FilmRow> filmRowsToSave) {
    try (var workbook = new XSSFWorkbook(new FileInputStream(xlsxPath))) {
      var filmsSheet = workbook.getSheetAt(Sheet.FILM.id());
      var xlsxRows = rowsOf(filmsSheet).toList();
      var savedRows = filmRowsToSave.stream()
              .map(filmRow -> Optional.ofNullable(filmRow.id())
                      .flatMap(id -> searchById(xlsxRows, id))
                      .map(row -> update(filmRow, row))
                      .orElseGet(() -> insert(filmRow, newRow(filmsSheet))))
              .toList();
      save(workbook);
      return savedRows.stream().map(this::adaptRow).toList();
    } catch (IOException | InvalidOperationException e) {
      logger.error("Error trying to read {}", xlsxPath, e);
      throw new InvalidXlsxFileException(xlsxPath, e);
    }
  }

  private Stream<Row> rowsOf(XSSFSheet sheet) {
    return StreamSupport.stream(sheet.spliterator(), false)
            .skip(Sheet.FILM.rowsToSkip())
            .filter(row -> !isEmpty(row.getCell(CellValue.ID.id())));
  }

  private void save(XSSFWorkbook workbook) throws IOException {
    try (var outFile = new FileOutputStream(xlsxPath)) {
      workbook.write(outFile);
    }
  }

  private Optional<Row> searchById(List<Row> xlsxRows, Long id) {
    return xlsxRows.stream()
            .filter(it -> it.getCell(CellValue.ID.id()).getNumericCellValue() == id)
            .findFirst();
  }

  private XSSFRow newRow(XSSFSheet filmsSheet) {
    return filmsSheet.createRow(filmsSheet.getLastRowNum() + 1);
  }

  private Row update(FilmRow filmRow, Row row) {
    row.getCell(CellValue.TITLE.id()).setCellValue(filmRow.title());
    row.getCell(CellValue.YEAR.id()).setCellValue(filmRow.year());
    row.getCell(CellValue.GENRES.id()).setCellValue(adaptGenres(filmRow.genres()));
    return row;
  }

  private Row insert(FilmRow filmRow, Row row) {
    Optional.ofNullable(filmRow.id()).ifPresentOrElse(
            it -> row.createCell(CellValue.ID.id()).setCellValue(it),
            () -> row.createCell(CellValue.ID.id()).setCellValue(row.getRowNum() - 2));
    row.createCell(CellValue.TITLE.id()).setCellValue(filmRow.title());
    row.createCell(CellValue.YEAR.id()).setCellValue(filmRow.year());
    row.createCell(CellValue.GENRES.id()).setCellValue(adaptGenres(filmRow.genres()));
    return row;
  }

  private FilmRow adaptRow(Row row) {
    return new FilmRow(
            (long) row.getCell(CellValue.ID.id()).getNumericCellValue(),
            row.getCell(CellValue.TITLE.id()).getStringCellValue(),
            (int) row.getCell(CellValue.YEAR.id()).getNumericCellValue(),
            adaptGenres(row),
            adaptReviews(row)
    );
  }

  private List<String> adaptGenres(Row row) {
    var genresCell = row.getCell(CellValue.GENRES.id());
    if (isEmpty(genresCell)) {
      return Collections.emptyList();
    }
    return Arrays.stream(genresCell.getStringCellValue().toLowerCase().split(", ")).toList();
  }

  private String adaptGenres(List<String> genres) {
    return String.join(", ", genres);
  }

  private List<FilmReviewRow> adaptReviews(Row row) {
    var reviewRows = new ArrayList<FilmReviewRow>();

    int i = CellValue.FIRST_REVIEW.id();
    while (i < row.getLastCellNum()) {
      var dateCell = row.getCell(i + 1);
      if (!isEmpty(dateCell)) {
        var commentCell = row.getCell(i + 2);
        reviewRows.add(
                new FilmReviewRow(
                        (long) row.getCell(i).getNumericCellValue(),
                        DateUtil.isCellDateFormatted(dateCell) ? dateCell.getLocalDateTimeCellValue() : FALLBACK_DATE,
                        (int) row.getCell(3).getNumericCellValue(),
                        isEmpty(commentCell) ? null : commentCell.getStringCellValue()
                )
        );
      }
      i += 3;
    }
    return reviewRows;
  }
}
