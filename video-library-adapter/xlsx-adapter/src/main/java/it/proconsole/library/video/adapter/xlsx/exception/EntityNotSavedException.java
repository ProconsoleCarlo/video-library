package it.proconsole.library.video.adapter.xlsx.exception;

import it.proconsole.library.video.adapter.xlsx.model.FilmRow;

public class EntityNotSavedException extends RuntimeException {
  public EntityNotSavedException(FilmRow entity) {
    super("Entity not saved on XLSX " + entity);
  }
}
