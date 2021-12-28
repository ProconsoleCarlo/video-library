package it.proconsole.library.video.adapter.jpa.repository.adapter;

import it.proconsole.library.video.adapter.jpa.model.GenreEntity;
import it.proconsole.library.video.core.model.Genre;
import it.proconsole.library.video.core.model.GenreEnum;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenreAdapterTest {
  private static final List<Genre> DOMAIN = Arrays.stream(GenreEnum.values()).map(it -> new Genre(it.id(), it)).toList();
  private static final List<GenreEntity> FRONTIER = Arrays.stream(GenreEnum.values()).map(it -> new GenreEntity(it.id(), it)).toList();

  private final GenreAdapter adapter = new GenreAdapter();

  @Test
  void fromDomain() {
    var current = adapter.fromDomain(DOMAIN);

    assertEquals(FRONTIER, current);
  }

  @Test
  void toDomain() {
    var current = adapter.toDomain(FRONTIER);

    assertEquals(DOMAIN, current);
  }
}