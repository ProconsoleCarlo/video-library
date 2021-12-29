package it.proconsole.library.video.adapter.jdbc.exception;

import it.proconsole.library.video.adapter.jdbc.model.EntityWithId;

public class EntityNotSavedException extends RuntimeException {
  public EntityNotSavedException(EntityWithId entity) {
    super("Entity not saved on DB " + entity);
  }
}
