package it.proconsole.library.video.adapter.xlsx.repository.workbook.adapter;

import it.proconsole.library.video.adapter.xlsx.model.FilmRow;
import it.proconsole.library.video.adapter.xlsx.repository.workbook.CellValue;
import org.apache.poi.ss.usermodel.Row;

import java.util.Optional;

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

  public Row toRow(FilmRow filmRow, Row row) {
    Optional.ofNullable(filmRow.id()).ifPresentOrElse(
            it -> row.getCell(CellValue.ID.id(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(it),
            () -> row.getCell(CellValue.ID.id(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(row.getRowNum() - 2));
    row.getCell(CellValue.TITLE.id(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(filmRow.title());
    row.getCell(CellValue.YEAR.id(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(filmRow.year());
    row.getCell(CellValue.GENRES.id(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(genreAdapter.toRowValue(filmRow.genres()));
    return row;
  }
}
