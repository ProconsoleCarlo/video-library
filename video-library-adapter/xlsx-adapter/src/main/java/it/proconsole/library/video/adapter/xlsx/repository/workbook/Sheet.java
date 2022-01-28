package it.proconsole.library.video.adapter.xlsx.repository.workbook;

public enum Sheet {
  FILM(0, 3);

  private final int id;
  private final int rowsToSkip;

  Sheet(int id, int rowsToSkip) {
    this.id = id;
    this.rowsToSkip = rowsToSkip;
  }

  public int id() {
    return id;
  }

  public int rowsToSkip() {
    return rowsToSkip;
  }
}
