package it.proconsole.library.video.adapter.xlsx.repository.workbook.adapter;

import it.proconsole.library.video.adapter.xlsx.model.FilmReviewRow;
import it.proconsole.library.video.adapter.xlsx.repository.workbook.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static it.proconsole.library.video.adapter.xlsx.repository.workbook.CellUtil.isEmpty;

public class FilmReviewValueAdapter {
  private static final LocalDateTime FALLBACK_DATE = LocalDateTime.of(2012, Month.JANUARY, 1, 0, 0);

  public List<FilmReviewRow> fromRow(Row row) {
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
                        (int) row.getCell(3).getNumericCellValue(), //TODO each review should have a rating
                        isEmpty(commentCell) ? null : commentCell.getStringCellValue()
                )
        );
      }
      i += 3;
    }
    return reviewRows;
  }
}
