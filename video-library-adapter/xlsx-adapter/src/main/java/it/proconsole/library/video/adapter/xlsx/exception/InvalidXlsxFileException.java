package it.proconsole.library.video.adapter.xlsx.exception;

public class InvalidXlsxFileException extends RuntimeException {
  public InvalidXlsxFileException(String xlsxPath, Throwable cause) {
    super("Error reading xlsx on " + xlsxPath, cause);
  }
}
