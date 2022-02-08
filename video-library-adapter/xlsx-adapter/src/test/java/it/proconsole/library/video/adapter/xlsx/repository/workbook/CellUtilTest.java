package it.proconsole.library.video.adapter.xlsx.repository.workbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CellUtilTest {
  @ParameterizedTest
  @MethodSource("emptyCells")
  @NullSource
  void isEmpty(Cell cell) {
    assertTrue(CellUtil.isEmpty(cell));
  }

  @ParameterizedTest
  @MethodSource("notEmptyCells")
  void isNotEmpty(Cell cell) {
    assertFalse(CellUtil.isEmpty(cell));
  }

  private static List<Cell> emptyCells() {
    var blankCell = mock(Cell.class);
    var emptyCell = mock(Cell.class);
    when(blankCell.getCellType()).thenReturn(CellType.BLANK);
    when(emptyCell.getCellType()).thenReturn(CellType.STRING);
    when(emptyCell.getStringCellValue()).thenReturn(" ");
    return List.of(blankCell, emptyCell);
  }

  private static List<Cell> notEmptyCells() {
    var stringCell = mock(Cell.class);
    when(stringCell.getCellType()).thenReturn(CellType.STRING);
    when(stringCell.getStringCellValue()).thenReturn("Hello");
    var numericCell = mock(Cell.class);
    when(numericCell.getCellType()).thenReturn(CellType.NUMERIC);
    var booleanCell = mock(Cell.class);
    when(booleanCell.getCellType()).thenReturn(CellType.BOOLEAN);
    var errorCell = mock(Cell.class);
    when(errorCell.getCellType()).thenReturn(CellType.ERROR);
    var formulaCell = mock(Cell.class);
    when(formulaCell.getCellType()).thenReturn(CellType.FORMULA);
    return List.of(stringCell, numericCell, booleanCell, errorCell, formulaCell);
  }
}