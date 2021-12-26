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
  }

  public List<FilmRow> findAll() {
    filmReviewId = 1L;
    try {
      var sheets = new XSSFWorkbook(xlsxPath);
      var filmsSheet = sheets.getSheetAt(FILM_SHEET);
      var spliterator = filmsSheet.spliterator();
      var films = StreamSupport.stream(spliterator, false)
              .skip(3)
              .filter(this::emptyRows)
              .map(this::adaptRow)
              .toList();
      sheets.close();
      return films;
    } catch (IOException | InvalidOperationException e) {
      logger.error("An error as occurred trying to read {}", xlsxPath, e);
      throw new InvalidXlsxFileException(e);
    }
  }

  private boolean emptyRows(Row row) {
    return row.getCell(0) != null;
  }

  private FilmRow adaptRow(Row row) {
    return new FilmRow(
            (long) row.getRowNum() - 2,
            row.getCell(CellValue.TITLE.id()).getStringCellValue(),
            (int) row.getCell(CellValue.YEAR.id()).getNumericCellValue(),
            row.getCell(CellValue.GENRES.id()).getStringCellValue(),
            adaptFilmReview(row)
    );
  }

  private List<FilmReviewRow> adaptFilmReview(Row row) {
    var reviewRows = new ArrayList<FilmReviewRow>();
    int i = CellValue.FIRST_REVIEW.id();
    while (i < row.getLastCellNum()) {
      DateComment.from(row.getCell(i), row.getCell(i + 1))
              .ifPresent(xlsxReview -> {
                        reviewRows.add(
                                new FilmReviewRow(
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
    private static final Logger logger = LoggerFactory.getLogger(DateComment.class);
    private static final LocalDateTime FALLBACK_DATE = LocalDateTime.of(2012, Month.JANUARY, 1, 0, 0);

    public static Optional<DateComment> from(Cell dateCell, @Nullable Cell commentCell) {
      try {
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
      } catch (Exception e) {
        logger.error("Error parsing review {} {}", dateCell, commentCell, e);
        return Optional.empty();
      }
    }
  }
}
