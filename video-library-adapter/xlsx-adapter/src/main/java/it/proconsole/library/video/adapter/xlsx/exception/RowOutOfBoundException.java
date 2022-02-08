package it.proconsole.library.video.adapter.xlsx.exception;

public class RowOutOfBoundException extends RuntimeException {
  public RowOutOfBoundException(int rowNumber) {
    super("Row " + rowNumber + " is out of bound");
  }
}
