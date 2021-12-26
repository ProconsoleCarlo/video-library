package it.proconsole.library.video.adapter.xlsx.exception;

public class UnknownGenreException extends RuntimeException {
  public UnknownGenreException(String genre) {
    super("Unknown genre " + genre + " found");
  }
}
