package it.proconsole.library.video.adapter.jdbc.exception;

import it.proconsole.library.video.adapter.jdbc.model.FilmEntity;

public class EntityNotSavedException extends RuntimeException {
  public EntityNotSavedException(FilmEntity film) {
    super("Film not saved on DB " + film);
  }
}
