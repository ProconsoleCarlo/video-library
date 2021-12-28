package it.proconsole.library.video.core.exception;

import it.proconsole.library.video.core.model.FilmReview;

public class FilmNotFoundException extends RuntimeException {
  public FilmNotFoundException(FilmReview review) {
    super("Film %d not found for review %s".formatted(review.filmId(), review));
  }
}
