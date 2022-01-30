package it.proconsole.library.video.adapter.xlsx.repository.workbook.adapter;

import it.proconsole.library.video.adapter.xlsx.model.FilmRow;
import it.proconsole.library.video.adapter.xlsx.repository.workbook.CellValue;
import org.apache.poi.ss.usermodel.Row;

public class FilmValueAdapter {
  private final GenreValueAdapter genreAdapter;
  private final FilmReviewValueAdapter filmReviewAdapter;

  public FilmValueAdapter(GenreValueAdapter genreAdapter, FilmReviewValueAdapter filmReviewAdapter) {
    this.genreAdapter = genreAdapter;
    this.filmReviewAdapter = filmReviewAdapter;
  }

  public FilmRow fromRow(Row row) {
    return new FilmRow(
            (long) row.getCell(CellValue.ID.id()).getNumericCellValue(),
            row.getCell(CellValue.TITLE.id()).getStringCellValue(),
            (int) row.getCell(CellValue.YEAR.id()).getNumericCellValue(),
            genreAdapter.fromRow(row),
            filmReviewAdapter.fromRow(row)
    );
  }
}
