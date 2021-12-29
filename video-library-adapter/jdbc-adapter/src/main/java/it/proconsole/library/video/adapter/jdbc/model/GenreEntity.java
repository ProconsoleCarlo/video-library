package it.proconsole.library.video.adapter.jdbc.model;

import it.proconsole.library.video.core.model.GenreEnum;
import org.springframework.lang.Nullable;

import java.util.Map;

public record GenreEntity(@Nullable Long id, GenreEnum value) implements EntityWithId {
  public GenreEntity(GenreEnum value) {
    this(value.id(), value);
  }

  @Override
  public Map<String, Object> data() {
    return Map.of("value", value.name());
  }
}
