package it.proconsole.library.video.adapter.jdbc.model;

import it.proconsole.library.video.core.model.Genre;
import it.proconsole.library.video.core.model.GenreEnum;
import org.springframework.lang.Nullable;

import java.util.Map;

public record GenreEntity(@Nullable Long id, GenreEnum value) implements EntityWithId {
  public GenreEntity(GenreEnum value) {
    this(value.id(), value);
  }

  public static GenreEntity fromDomain(Genre genre) {
    return new GenreEntity(genre.id(), genre.value());
  }

  public Genre toDomain() {
    return new Genre(id, value);
  }

  @Override
  public Map<String, Object> data() {
    return Map.of("value", value.name());
  }
}
