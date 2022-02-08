package it.proconsole.library.video.adapter.xlsx.repository.workbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.lang.Nullable;

public class CellUtil {
  private CellUtil() {
  }

  public static boolean isEmpty(@Nullable Cell cell) {
    return cell == null
            || cell.getCellType() == CellType.BLANK
            || (cell.getCellType() == CellType.STRING && cell.getStringCellValue().isBlank());
  }
}
