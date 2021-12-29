package it.proconsole.library.video.adapter.jdbc.model;

import org.springframework.lang.Nullable;

import java.util.Map;

public record FilmEntity(@Nullable Long id, String title, Integer year) implements EntityWithId {
  public FilmEntity(String title, Integer year) {
    this(null, title, year);
  }

  @Override
  public Map<String, Object> data() {
    return Map.of(
            "title", title,
            "year", year
    );
  }
}
