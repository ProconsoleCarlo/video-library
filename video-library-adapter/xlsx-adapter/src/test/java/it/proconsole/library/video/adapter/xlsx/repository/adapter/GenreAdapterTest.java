package it.proconsole.library.video.adapter.xlsx.repository.adapter;

import it.proconsole.library.video.adapter.xlsx.exception.UnknownGenreException;
import it.proconsole.library.video.core.model.Genre;
import it.proconsole.library.video.core.model.GenreEnum;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GenreAdapterTest {
  private static final List<Genre> DOMAIN = Arrays.stream(GenreEnum.values()).map(it -> new Genre(it.id(), it)).toList();
  private static final String FRONTIER = "Azione, avventura, biografico, commedia, crimine, catastrofico, documentario, drammatico, erotico, fantastico, storico, horror, romantico, fantascienza, suspense/thriller, western";

  private final GenreAdapter adapter = new GenreAdapter();

  @Test
  void fromDomain() {
    var current = adapter.fromDomain(DOMAIN);

    assertEquals(FRONTIER, current);
  }

  @Nested
  class WhenToDomain {
    @Test
    void adapt() {
      var current = adapter.toDomain(FRONTIER);

      assertEquals(DOMAIN, current);
    }

    @Test
    void adaptEmptyString() {
      var current = adapter.toDomain("");

      assertEquals(Collections.emptyList(), current);
    }

    @Test
    void failsWhenUnknownGenre() {
      assertThrows(UnknownGenreException.class, () -> adapter.toDomain("not existent genre"));
    }
  }
}