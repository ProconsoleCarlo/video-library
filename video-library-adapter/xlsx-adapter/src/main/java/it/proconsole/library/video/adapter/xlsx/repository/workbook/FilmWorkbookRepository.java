package it.proconsole.library.video.adapter.xlsx.repository.workbook;

import it.proconsole.library.video.adapter.xlsx.exception.InvalidXlsxFileException;
import it.proconsole.library.video.adapter.xlsx.model.FilmReviewRow;
import it.proconsole.library.video.adapter.xlsx.model.FilmRow;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
import java.util.Optional;
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

  public void deleteAll() {
    try (var workbook = new XSSFWorkbook(new FileInputStream(xlsxPath))) {
      var filmsSheet = workbook.getSheetAt(FILM_SHEET);

      for (int i = 3; i <= filmsSheet.getLastRowNum(); i++) {
        filmsSheet.removeRow(filmsSheet.getRow(i));
      }

      try (var outFile = new FileOutputStream(xlsxPath)) {
        workbook.write(outFile);
      }

    } catch (IOException | InvalidOperationException e) {
      logger.error("Error trying to read {}", xlsxPath, e);
      throw new InvalidXlsxFileException(xlsxPath, e);
    }
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

  public List<FilmRow> findAllById(List<Long> ids) {
    return findAll().stream().filter(it -> ids.contains(it.id())).toList();
  }

  public List<FilmRow> saveAll(List<FilmRow> filmRowsToSave) {
    try (var workbook = new XSSFWorkbook(new FileInputStream(xlsxPath))) {
      var filmsSheet = workbook.getSheetAt(FILM_SHEET);
      var spliterator = filmsSheet.spliterator();

      var xlsxRows = StreamSupport.stream(spliterator, false)
              .skip(3)
              .filter(row -> !isEmpty(row.getCell(CellValue.ID.id())))
              .toList();

      filmRowsToSave.forEach(filmRow -> {
        if (filmRow.id() == null) {
          //insert row (new film
        } else {
          xlsxRows.stream().filter(it -> it.getCell(CellValue.ID.id()).getNumericCellValue() == filmRow.id())
                  .findFirst()
                  .ifPresentOrElse(
                          row -> update(filmRow, row),
                          () -> insert(filmRow, newRow(filmsSheet))
                  );
        }
      });

      try (var outFile = new FileOutputStream(xlsxPath)) {
        workbook.write(outFile);
      }

    } catch (IOException | InvalidOperationException e) {
      logger.error("Error trying to read {}", xlsxPath, e);
      throw new InvalidXlsxFileException(xlsxPath, e);
    }

    return findAll();
  }

  private XSSFRow newRow(XSSFSheet filmsSheet) {
    return filmsSheet.createRow(filmsSheet.getLastRowNum() + 1);
  }

  private void update(FilmRow filmRow, Row row) {
    row.getCell(CellValue.TITLE.id()).setCellValue(filmRow.title());
    row.getCell(CellValue.YEAR.id()).setCellValue(filmRow.year());
    row.getCell(CellValue.GENRES.id()).setCellValue(adaptGenres(filmRow.genres()));
  }

  private void insert(FilmRow filmRow, Row row) {
    Optional.ofNullable(filmRow.id()).ifPresentOrElse(
            it -> row.createCell(CellValue.ID.id()).setCellValue(it),
            () -> row.createCell(CellValue.ID.id()).setCellValue(row.getRowNum() - 2));
    row.createCell(CellValue.TITLE.id()).setCellValue(filmRow.title());
    row.createCell(CellValue.YEAR.id()).setCellValue(filmRow.year());
    row.createCell(CellValue.GENRES.id()).setCellValue(adaptGenres(filmRow.genres()));
  }

  private FilmRow adaptRow(Row row) {
    var id = (long) row.getRowNum() - 2;
    if (!isEmpty(row.getCell(CellValue.ID.id()))) {
      id = (long) row.getCell(CellValue.ID.id()).getNumericCellValue();
    }
    return new FilmRow(
            id,
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
