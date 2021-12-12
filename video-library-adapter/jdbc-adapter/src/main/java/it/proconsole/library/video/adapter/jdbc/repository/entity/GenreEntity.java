package it.proconsole.library.video.adapter.jdbc.repository.entity;

import it.proconsole.library.video.adapter.jdbc.model.Genre;
import it.proconsole.library.video.core.model.GenreEnum;

public record GenreEntity(Long id, GenreEnum value) implements EntityWithId {
  public GenreEntity(GenreEnum value) {
    this(1L, value);
  }

  public Genre toDomain() {
    return new Genre(id, value);
  }
}
