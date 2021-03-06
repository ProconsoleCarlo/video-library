package it.proconsole.library.video.adapter.xlsx.repository.workbook;

import it.proconsole.library.video.adapter.xlsx.exception.EntityNotSavedException;
import it.proconsole.library.video.adapter.xlsx.exception.InvalidXlsxFileException;
import it.proconsole.library.video.adapter.xlsx.exception.RowOutOfBoundException;
import it.proconsole.library.video.adapter.xlsx.model.FilmRow;
import it.proconsole.library.video.adapter.xlsx.repository.workbook.adapter.FilmValueAdapter;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static it.proconsole.library.video.adapter.xlsx.repository.workbook.CellUtil.isEmpty;

public class FilmWorkbookRepository {
  private final String xlsxPath;
  private final FilmValueAdapter filmValueAdapter;

  public FilmWorkbookRepository(String xlsxPath, FilmValueAdapter filmValueAdapter) {
    this.xlsxPath = xlsxPath;
    this.filmValueAdapter = filmValueAdapter;
  }

  public void delete(FilmRow entity) {
    Optional.ofNullable(entity.id()).ifPresent(this::deleteById);
  }

  public void deleteAll() {
    try (var workbook = new XSSFWorkbook(new FileInputStream(xlsxPath))) {
      var filmsSheet = workbook.getSheetAt(SheetValue.FILM.id());
      IntStream.rangeClosed(SheetValue.FILM.rowsToSkip(), filmsSheet.getLastRowNum())
              .forEach(i -> filmsSheet.removeRow(filmsSheet.getRow(i)));
      save(workbook);
    } catch (IOException | InvalidOperationException e) {
      throw new InvalidXlsxFileException(xlsxPath, e);
    }
  }

  public void deleteAll(List<FilmRow> entities) {
    deleteAllById(entities.stream().map(FilmRow::id).toList());
  }

  public void deleteById(Long id) {
    deleteAllById(List.of(id));
  }

  public void deleteAllById(List<Long> ids) {
    try (var workbook = new XSSFWorkbook(new FileInputStream(xlsxPath))) {
      var filmsSheet = workbook.getSheetAt(SheetValue.FILM.id());
      ids.forEach(id ->
              IntStream.rangeClosed(SheetValue.FILM.rowsToSkip(), filmsSheet.getLastRowNum())
                      .filter(i -> filmsSheet.getRow(i).getCell(CellValue.ID.id()).getNumericCellValue() == id)
                      .findFirst()
                      .ifPresent(i -> deleteRow(filmsSheet, i))
      );

      save(workbook);
    } catch (IOException | InvalidOperationException e) {
      throw new InvalidXlsxFileException(xlsxPath, e);
    }
  }

  public List<FilmRow> findAll() {
    try (var sheets = new XSSFWorkbook(xlsxPath)) {
      return rowsOf(sheets.getSheetAt(SheetValue.FILM.id())).map(filmValueAdapter::fromRow).toList();
    } catch (IOException | InvalidOperationException e) {
      throw new InvalidXlsxFileException(xlsxPath, e);
    }
  }

  public List<FilmRow> findAllById(List<Long> ids) {
    return findAll().stream().filter(it -> ids.contains(it.id())).toList();
  }

  public Optional<FilmRow> findById(Long id) {
    return findAllById(List.of(id)).stream().findFirst();
  }

  public FilmRow save(FilmRow entity) {
    return saveAll(List.of(entity)).stream().findFirst().orElseThrow(() -> new EntityNotSavedException(entity));
  }

  public List<FilmRow> saveAll(List<FilmRow> filmRowsToSave) {
    try (var workbook = new XSSFWorkbook(new FileInputStream(xlsxPath))) {
      var filmsSheet = workbook.getSheetAt(SheetValue.FILM.id());
      var xlsxRows = rowsOf(filmsSheet).toList();
      var savedRows = filmRowsToSave.stream()
              .map(filmRow -> Optional.ofNullable(filmRow.id())
                      .flatMap(id -> searchById(xlsxRows, id))
                      .map(row -> filmValueAdapter.toRow(filmRow, row))
                      .orElseGet(() -> filmValueAdapter.toRow(filmRow, newRow(filmsSheet))))
              .toList();
      save(workbook);
      return savedRows.stream().map(filmValueAdapter::fromRow).toList();
    } catch (IOException | InvalidOperationException e) {
      throw new InvalidXlsxFileException(xlsxPath, e);
    }
  }

  private void deleteRow(Sheet sheet, int rowNumber) {
    var lastRowNum = sheet.getLastRowNum();
    if (rowNumber >= 0 && rowNumber < lastRowNum) {
      sheet.shiftRows(rowNumber + 1, lastRowNum, -1);
    } else if (rowNumber == lastRowNum) {
      Optional.ofNullable(sheet.getRow(rowNumber)).ifPresent(sheet::removeRow);
    } else {
      throw new RowOutOfBoundException(rowNumber);
    }
  }

  private Row newRow(Sheet filmsSheet) {
    return filmsSheet.createRow(filmsSheet.getLastRowNum() + 1);
  }

  private Stream<Row> rowsOf(Sheet sheet) {
    return StreamSupport.stream(sheet.spliterator(), false)
            .skip(SheetValue.FILM.rowsToSkip())
            .filter(row -> !isEmpty(row.getCell(CellValue.ID.id())));
  }

  private void save(Workbook workbook) throws IOException {
    try (var outFile = new FileOutputStream(xlsxPath)) {
      workbook.write(outFile);
    }
  }

  private Optional<Row> searchById(List<Row> xlsxRows, Long id) {
    return xlsxRows.stream()
            .filter(it -> it.getCell(CellValue.ID.id()).getNumericCellValue() == id)
            .findFirst();
  }
}
