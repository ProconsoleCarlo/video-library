package it.proconsole.library.video.adapter.xlsx.repository.workbook;

public enum CellValue {
  ID(0),
  TITLE(1),
  YEAR(2),
  RATING(3),
  GENRES(4),
  FIRST_REVIEW(17);

  private final int id;

  CellValue(int id) {
    this.id = id;
  }

  public int id() {
    return id;
  }
}
