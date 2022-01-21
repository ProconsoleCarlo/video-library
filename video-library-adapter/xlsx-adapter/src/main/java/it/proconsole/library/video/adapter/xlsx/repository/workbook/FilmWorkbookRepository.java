package it.proconsole.library.video.adapter.xlsx.repository.workbook;

import it.proconsole.library.video.adapter.xlsx.exception.InvalidXlsxFileException;
import it.proconsole.library.video.adapter.xlsx.model.FilmReviewRow;
import it.proconsole.library.video.adapter.xlsx.model.FilmRow;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.StreamSupport;

import static it.proconsole.library.video.adapter.xlsx.repository.workbook.CellUtil.isEmpty;

public class FilmWorkbookRepository {
  private static final int FILM_SHEET = 0;
  private static final LocalDateTime FALLBACK_DATE = LocalDateTime.of(2012, Month.JANUARY, 1, 0, 0);

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final String xlsxPath;

  private long filmReviewId = 0L;

  public FilmWorkbookRepository(String xlsxPath) {
    this.xlsxPath = xlsxPath;
  }

  public List<FilmRow> findAll() {
    filmReviewId = 1L;
    try (var sheets = new XSSFWorkbook(xlsxPath)) {
      var filmsSheet = sheets.getSheetAt(FILM_SHEET);
      var spliterator = filmsSheet.spliterator();
      return StreamSupport.stream(spliterator, false)
              .skip(3)
              .filter(row -> !isEmpty(row.getCell(0)))
              .map(this::adaptRow)
              .toList();
    } catch (IOException | InvalidOperationException e) {
      logger.error("Error trying to read {}", xlsxPath, e);
      throw new InvalidXlsxFileException(xlsxPath, e);
    }
  }

  public List<FilmRow> saveAll(List<FilmRow> filmRowsToSave) {
    try (var workbook = new XSSFWorkbook(new FileInputStream(xlsxPath))) {
      var filmsSheet = workbook.getSheetAt(FILM_SHEET);
      var spliterator = filmsSheet.spliterator();

      var xlsxRows = StreamSupport.stream(spliterator, false)
              .skip(3)
              .filter(row -> !isEmpty(row.getCell(0)))
              .toList();
      var rowsToSave = filmRowsToSave.stream()
              .map(filmRow -> {
                if (filmRow.id() == null) {
                  //insert row (new film
                  return Collections.emptyList();
                } else {
                  return xlsxRows.stream().filter(it -> it.getCell(0).getNumericCellValue() == filmRow.id())
                          .findFirst()
                          .map(row -> saveFilm(filmRow, row));
                  //.orElseGet(() -> insert row (film on db but not on xlsx))
                }
              }).toList();

      try (var outFile = new FileOutputStream(xlsxPath)) {
        workbook.write(outFile);
      }

    } catch (IOException | InvalidOperationException e) {
      logger.error("Error trying to read {}", xlsxPath, e);
      throw new InvalidXlsxFileException(xlsxPath, e);
    }

    return findAll();
  }

  private Row saveFilm(FilmRow filmRow, Row row) {
    row.getCell(CellValue.TITLE.id()).setCellValue(filmRow.title());
    row.getCell(CellValue.YEAR.id()).setCellValue(filmRow.year());
    row.getCell(CellValue.GENRES.id()).setCellValue(adaptGenres(filmRow.genres()));
    return row;
  }

  private FilmRow adaptRow(Row row) {
    return new FilmRow(
            (long) row.getRowNum() - 2,
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
                        filmReviewId(row.getCell(i)),
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

  private Long filmReviewId(@Nullable Cell cell) {
    if (isEmpty(cell)) {
      return filmReviewId++;
    } else {
      return (long) cell.getNumericCellValue();
    }
  }
}
