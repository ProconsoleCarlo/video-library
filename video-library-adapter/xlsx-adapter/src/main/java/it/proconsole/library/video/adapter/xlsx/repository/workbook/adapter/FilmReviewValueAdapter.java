package it.proconsole.library.video.adapter.xlsx.repository.workbook.adapter;

import it.proconsole.library.video.adapter.xlsx.model.FilmReviewRow;
import it.proconsole.library.video.adapter.xlsx.repository.workbook.CellUtil;
import it.proconsole.library.video.adapter.xlsx.repository.workbook.CellValue;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static it.proconsole.library.video.adapter.xlsx.repository.workbook.CellUtil.isEmpty;

public class FilmReviewValueAdapter {
  private static final LocalDateTime FALLBACK_DATE = LocalDateTime.of(2012, Month.JANUARY, 1, 0, 0);

  public List<FilmReviewRow> fromRow(Row row) {
    var reviewRows = new ArrayList<FilmReviewRow>();
    var i = CellValue.FIRST_REVIEW.id();
    while (i < row.getLastCellNum()) {
      var dateCell = row.getCell(i + 1);
      if (!isEmpty(dateCell)) {
        var commentCell = row.getCell(i + 3);
        reviewRows.add(
                new FilmReviewRow(
                        filmReviewId(row, i),
                        filmReviewDate(dateCell),
                        filmReviewRating(row, i),
                        isEmpty(commentCell) ? null : commentCell.getStringCellValue()
                )
        );
      }
      i = nextReview(i);
    }
    return reviewRows;
  }

  private LocalDateTime filmReviewDate(Cell dateCell) {
    if (dateCell.getNumericCellValue() == 2012) {
      return FALLBACK_DATE;
    } else {
      return dateCell.getLocalDateTimeCellValue();
    }
  }

  private Long filmReviewId(Row row, int i) {
    if (isEmpty(row.getCell(i))) {
      return null;
    } else {
      return (long) row.getCell(i).getNumericCellValue();
    }
  }

  private int filmReviewRating(Row row, int i) {
    if (CellUtil.isEmpty(row.getCell(i + 2))) {
      return (int) row.getCell(CellValue.RATING.id()).getNumericCellValue();
    } else {
      return (int) row.getCell(i + 2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue();
    }
  }

  public Row toRow(List<FilmReviewRow> filmReviews, Row row) {
    var i = CellValue.FIRST_REVIEW.id();
    for (FilmReviewRow filmReview : filmReviews) {
      var cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
      Optional.ofNullable(filmReview.id())
              .ifPresent(cell::setCellValue); //TODO define the id with orElse
      row.getCell(i + 1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(filmReview.date());
      row.getCell(i + 2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(filmReview.rating());
      Optional.ofNullable(filmReview.detail())
              .ifPresent(row.getCell(i + 3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)::setCellValue);
      i = nextReview(i);
    }

    IntStream.rangeClosed(i, row.getLastCellNum())
            .forEach(id -> row.removeCell(row.getCell(id, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)));

    return row;
  }

  private int nextReview(int i) {
    return i + 4;
  }
}
