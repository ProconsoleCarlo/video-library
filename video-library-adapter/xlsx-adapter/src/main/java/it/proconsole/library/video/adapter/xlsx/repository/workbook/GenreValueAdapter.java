package it.proconsole.library.video.adapter.xlsx.repository.workbook;

import org.apache.poi.ss.usermodel.Row;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static it.proconsole.library.video.adapter.xlsx.repository.workbook.CellUtil.isEmpty;

public class GenreValueAdapter {
  private static final String GENRE_SEPARATOR = ", ";

  public List<String> fromRowValue(Row row) {
    var genresCell = row.getCell(CellValue.GENRES.id());
    if (isEmpty(genresCell)) {
      return Collections.emptyList();
    }
    return Arrays.stream(genresCell.getStringCellValue().toLowerCase().split(GENRE_SEPARATOR)).toList();
  }

  public String toRowValue(List<String> genres) {
    return String.join(GENRE_SEPARATOR, genres);
  }
}
