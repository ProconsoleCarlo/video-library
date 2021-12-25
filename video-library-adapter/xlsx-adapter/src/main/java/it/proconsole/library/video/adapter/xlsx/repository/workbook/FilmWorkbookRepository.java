package it.proconsole.library.video.adapter.xlsx.repository.workbook;

import it.proconsole.library.video.adapter.xlsx.exception.InvalidXlsxFileException;
import it.proconsole.library.video.adapter.xlsx.model.FilmRow;
import it.proconsole.library.video.adapter.xlsx.model.ReviewRow;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class FilmWorkbookRepository {
  private static final int FILM_SHEET = 0;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final String xlsxPath;

  private long filmReviewId = 1L;

  public FilmWorkbookRepository(String xlsxPath) {
    this.xlsxPath = xlsxPath;
    //"./src/main/resources/CatalogoFilm.xlsx"
  }

  public List<FilmRow> findAll() {
    filmReviewId = 1L;
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
            (long) row.getRowNum() - 2,
            row.getCell(0).getStringCellValue(),
            (int) row.getCell(1).getNumericCellValue(),
            row.getCell(3).getStringCellValue(),
            adaptFilmReview(row)
    );
  }

  private List<ReviewRow> adaptFilmReview(Row row) {
    var reviewRows = new ArrayList<ReviewRow>();
    int i = 16;
    while (i < row.getLastCellNum()) {
      DateComment.from(row.getCell(i), row.getCell(i + 1))
              .ifPresent(xlsxReview -> {
                        reviewRows.add(
                                new ReviewRow(
                                        filmReviewId,
                                        xlsxReview.date,
                                        (int) row.getCell(2).getNumericCellValue(),
                                        xlsxReview.comment
                                )
                        );
                        filmReviewId++;
                      }
              );
      i = i + 2;
    }
    return reviewRows;
  }

  private record DateComment(LocalDateTime date, @Nullable String comment) {
    private static final LocalDateTime FALLBACK_DATE = LocalDateTime.of(2012, Month.JANUARY, 1, 0, 0);

    public static Optional<DateComment> from(Cell dateCell, @Nullable Cell commentCell) {
      var date = DateUtil.isCellDateFormatted(dateCell) ? dateCell.getLocalDateTimeCellValue() : FALLBACK_DATE;
      var comment = Optional.ofNullable(commentCell)
              .map(Cell::getStringCellValue)
              .filter(it -> !it.isBlank())
              .orElse(null);
      if (date != null) {
        return Optional.of(new DateComment(date, comment));
      } else {
        return Optional.empty();
      }
    }
  }
}
