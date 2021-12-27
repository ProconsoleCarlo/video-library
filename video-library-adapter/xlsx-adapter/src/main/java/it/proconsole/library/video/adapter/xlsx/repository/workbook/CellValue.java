package it.proconsole.library.video.adapter.xlsx.repository.workbook;

public enum CellValue {
  TITLE(0),
  YEAR(1),
  GENRES(3),
  FIRST_REVIEW(16);

  private final int id;

  CellValue(int id) {
    this.id = id;
  }

  public int id() {
    return id;
  }
}
